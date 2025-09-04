package dev.salonce.githubrepositories.infrastructure;

import dev.salonce.githubrepositories.infrastructure.dtos.BranchDto;
import dev.salonce.githubrepositories.infrastructure.dtos.GithubRepositoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class GithubRestClient {

    public GithubRestClient(RestClient.Builder restClientBuilder, @Value("${github.token}") String githubToken, @Value("${github.base-url}") String githubBaseUrl){
        this.restClient = restClientBuilder
                .baseUrl(githubBaseUrl)
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .build();
    }
    private final RestClient restClient;

    public List<GithubRepositoryDto> getUserRepositories(String username) {
        GithubRepositoryDto[] repos = restClient
                .get()
                .uri("/users/" + username + "/repos")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, (status, response) -> {
                    throw new UserNotFoundException("User " + username + " not found.");
                })
                .body(GithubRepositoryDto[].class);
        if (repos == null) throw new GithubRepositoryIsNullException("Error. Github repository returned a null value.");
        return Arrays.asList(repos);
    }

    public List<BranchDto> getBranches(String username, String repoName) {
        BranchDto[] branchesArray = restClient
                .get()
                .uri("/repos/" + username + "/" + repoName + "/branches")
                .retrieve()
                .body(BranchDto[].class);
        if (branchesArray == null) throw new GithubBranchIsNullException("Error. One of the repository branches returned a null value.");
        return Arrays.asList(branchesArray);
    }
}
