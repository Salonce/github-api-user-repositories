package dev.salonce.atiperatask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0) // Random port for mocking GitHub
class GithubIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setupStubs() {
        // Mock GitHub API call
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/users/testuser/repos"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            [
                              {
                                "name": "repo1",
                                "fork": false,
                                "owner": { "login": "testuser" }
                              },
                              {
                                "name": "repo2",
                                "fork": true,
                                "owner": { "login": "testuser" }
                              }
                            ]
                            """)));

        // Mock branches for non-fork repo
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/testuser/repo1/branches"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            [
                              {
                                "name": "main",
                                "commit": { "sha": "abc123" }
                              }
                            ]
                            """)));
    }

    @Test
    void shouldReturnNonForkRepositories() throws Exception {
        webTestClient.get()
                .uri("/users/testuser/repositories")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("""
                [
                  {
                    "name": "repo1",
                    "owner_login": "testuser",
                    "branches": [
                      {
                        "name": "main",
                        "commitDto": {
                          "sha": "abc123"
                        }
                      }
                    ]
                  }
                ]
                """, true); // true = strict mode in JSONAssert
    }
}
