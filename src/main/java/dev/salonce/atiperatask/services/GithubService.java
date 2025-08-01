package dev.salonce.atiperatask.services;

import dev.salonce.atiperatask.dtos.BranchDto;
import dev.salonce.atiperatask.dtos.GithubRepositoryDto;
import dev.salonce.atiperatask.exceptions.NullDataException;
import dev.salonce.atiperatask.exceptions.UserNotFoundException;
import dev.salonce.atiperatask.models.Branch;
import dev.salonce.atiperatask.models.GithubRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GithubService {

    public GithubService(RestClient.Builder restClientBuilder, @Value("${github.token}") String githubToken){
        this.restClient = restClientBuilder
                .baseUrl("https://api.github.com")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .build();

        this.githubToken = githubToken;
    }

    @Value("${github.token}")
    private final String githubToken;
    private final RestClient restClient;

    private List<GithubRepositoryDto> getUserRepositories(String username) {
        GithubRepositoryDto[] repos = restClient
                .get()
                .uri("/users/" + username + "/repos")
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, (status, response) -> {
                    throw new UserNotFoundException("User " + username + " not found.");
                })
                .body(GithubRepositoryDto[].class);
        if (repos == null) throw new NullDataException("Incoming data is wrong. The repository returned a null value.");
        return Arrays.asList(repos);
    }

    private List<BranchDto> getBranches(String username, String repoName) {
            BranchDto[] branchesArray = restClient
                    .get()
                    .uri("/repos/" + username + "/" + repoName + "/branches")
                    .retrieve()
                    .body(BranchDto[].class);
            if (branchesArray == null) throw new NullDataException("Incoming data is wrong. Branches returned a null value.");
            return Arrays.asList(branchesArray);
    }

    public List<GithubRepository> getRepositoriesInformation(String username){
        List<GithubRepositoryDto> userGithubRepos = getUserRepositories(username);

        List<GithubRepository> githubRepositoryList = new ArrayList<>();
        for(GithubRepositoryDto githubRepositoryDto : userGithubRepos){
            if (githubRepositoryDto.getFork()) continue;

            String repoName = githubRepositoryDto.getName();
            String ownerLogin = githubRepositoryDto.getOwner().getLogin();

            List<BranchDto> branchDtos = getBranches(username, repoName);

            List<Branch> branches = new ArrayList<>();
            for (BranchDto branchDto : branchDtos){
                branches.add(new Branch(branchDto.getName(), branchDto.getCommitDto().getSha()));
            }

            GithubRepository githubRepository = new GithubRepository(repoName, ownerLogin, branches);
            githubRepositoryList.add(githubRepository);
        }
        return githubRepositoryList;
    }
}