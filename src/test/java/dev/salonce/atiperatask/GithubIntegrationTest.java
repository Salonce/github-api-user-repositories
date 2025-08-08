package dev.salonce.atiperatask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GithubIntegrationTest {

    @RegisterExtension
    static WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(WireMockConfiguration.options().dynamicPort())
            .build();

    @DynamicPropertySource
    static void overrideGithubBaseUrl(DynamicPropertyRegistry registry) {
        System.out.println("WireMock server host: " + wireMock.baseUrl());
        System.out.println("WireMock server port: " + wireMock.getPort());
        registry.add("github.base-url", () ->  wireMock.baseUrl());
    }

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setupStubs() {
        // Mock GitHub API call
        wireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/users/testuser/repos"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        // language=JSON
                        .withBody("""
                                [
                                      {
                                           "name": "sample-api",
                                           "owner": {
                                             "login": "testuser01",
                                             "irrelevant_field": "irrelevant info"
                                           },
                                           "fork": false,
                                           "irrelevant_field": "irrelevant info"
                                         },
                                         {
                                           "name": "sample-api2",
                                           "owner": {
                                             "login": "testuser02",
                                             "irrelevant_field": "irrelevant info"
                                           },
                                           "fork": true,
                                           "irrelevant_field": "irrelevant info"
                                         },
                                         {
                                           "name": "sample-api3",
                                           "owner": {
                                             "login": "testuser01",
                                             "irrelevant_field": "irrelevant info"
                                           },
                                           "fork": false,
                                           "irrelevant_field": "irrelevant info"
                                         }
                                    ]
                            """)));

        // Mock branches for non-fork repo
        wireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/testuser/sample-api/branches"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        // language=JSON
                        .withBody("""
                            [
                              {
                                "name": "main",
                                "commit": { "sha": "mainsha123" }
                              },
                              {
                                "name": "left",
                                "commit": { "sha": "leftbranch123" }
                              }
                            ]
                            """)));
        // forked repo - shouldn't appear
        wireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/testuser/sample-api2/branches"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        // language=JSON
                        .withBody("""
                            [
                              {
                                "name": "main",
                                "commit": { "sha": "mainsha321" }
                              }
                            ]
                            """)));
        wireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/repos/testuser/sample-api3/branches"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        // language=JSON
                        .withBody("""
                            []
                            """)));
    }

    @Test
    void shouldReturnNonForkRepositories() throws Exception {
        webTestClient.get()
                .uri("/users/testuser/repositories")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                // language=JSON
                .json("""
                [
                  {
                    "name": "sample-api",
                    "owner_login": "testuser01",
                    "branches": [
                      {
                        "name": "main",
                        "sha": "mainsha123"
                      },
                      {
                        "name": "left",
                        "sha": "leftbranch123"
                      }
                    ]
                  },
                  {
                    "name": "sample-api3",
                    "owner_login": "testuser01",
                    "branches": []
                  }
                ]
                """, true); // true = strict mode in JSONAssert
    }
}
