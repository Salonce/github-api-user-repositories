package dev.salonce.atiperatask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenValidUsername_whenCallingRepositoriesEndpoint_thenReturnRepositoriesWithBranches() {
        // GIVEN
        String username = "octocat"; // Safe test user
        String url = "/users/" + username + "/repositories";

        // WHEN
        ResponseEntity<Repository[]> response =
                restTemplate.getForEntity(url, Repository[].class);

        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Repository[] repositories = response.getBody();
        assertThat(repositories).withFailMessage("Program return null when trying to read octocat repositories.").isNotNull();
        assertThat(repositories.length).withFailMessage("Program found zero repositories for octocat, expected at least one.").isGreaterThan(0);

        Repository firstRepo = repositories[0];
        assertThat(firstRepo.getName())
                .withFailMessage("First repository's name was blank or null, expected a valid name.")
                .isNotBlank();

        assertThat(firstRepo.getOwner_login())
                .withFailMessage("First repository owner login did not match 'octocat' (case insensitive).")
                .isEqualToIgnoringCase("octocat");

        assertThat(firstRepo.getBranches())
                .withFailMessage("Branches list of first repository was null, expected non-null list.")
                .isNotNull();

        assertThat(firstRepo.getBranches().size())
                .withFailMessage("Branches list of first repository was empty, expected at least one branch.")
                .isGreaterThan(0);

        Branch branch = firstRepo.getBranches().get(0);

        assertThat(branch.getName())
                .withFailMessage("Branch name was blank or null, expected a valid branch name.")
                .isNotBlank();

        assertThat(branch.getSha().matches("[a-f0-9]{40}"))
                .withFailMessage("Branch SHA did not match expected 40-character hexadecimal format.")
                .isTrue();
    }
}
