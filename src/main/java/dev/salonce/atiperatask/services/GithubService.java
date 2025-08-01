package dev.salonce.atiperatask.services;

import dev.salonce.atiperatask.dtos.BranchDto;
import dev.salonce.atiperatask.dtos.GithubRepositoryDto;
import dev.salonce.atiperatask.exceptions.UserNotFoundException;
import dev.salonce.atiperatask.models.Branch;
import dev.salonce.atiperatask.models.GithubRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GithubService {

    @Value("${github.token}")
    private String githubToken;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GITHUB_API = "https://api.github.com";

    private List<GithubRepositoryDto> getUserRepositories(String username) {
        String url = GITHUB_API + "/users/" + username + "/repos";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<GithubRepositoryDto[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    GithubRepositoryDto[].class
            );

            GithubRepositoryDto[] reposArray = response.getBody();
            if (reposArray == null) return Collections.emptyList();
            return Arrays.asList(reposArray);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User '" + username + "' not found.");
        }
    }

    private List<BranchDto> getBranches(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<BranchDto[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    BranchDto[].class
            );

            BranchDto[] branchesArray = response.getBody();
            if (branchesArray == null) return Collections.emptyList();
            return Arrays.asList(branchesArray);
        } catch (Exception e) {
            System.out.println("Error fetching branches");
            return List.of();
        }
    }


    public List<GithubRepository> getRepositoriesInformation(String username){
        List<GithubRepositoryDto> userGithubRepos = getUserRepositories(username);

        List<GithubRepository> githubRepositoryList = new ArrayList<>();
        for(GithubRepositoryDto githubRepositoryDto : userGithubRepos){
            if (githubRepositoryDto.getFork()) continue;

            String name = githubRepositoryDto.getName();
            String ownerLogin = githubRepositoryDto.getOwner().getLogin();

            String branches_url = GITHUB_API + "/repos/" + username + "/" + name + "/branches";

            List<BranchDto> branchDtos = getBranches(branches_url);

            List<Branch> branches = new ArrayList<>();
            for (BranchDto branchDto : branchDtos){
                branches.add(new Branch(branchDto.getName(), branchDto.getCommitDto().getSha()));
            }

            GithubRepository githubRepository = new GithubRepository(name, ownerLogin, branches);
            githubRepositoryList.add(githubRepository);
        }
        return githubRepositoryList;
    }


//    private List<BranchDto> getBranches(String url) {
//        ResponseEntity<BranchDto[]> response;
//        try {
//            response = restTemplate.getForEntity(url, BranchDto[].class);
//        } catch (Exception e) {
//            throw new UserNotFoundException("Branches not found.");
//        }
//
//        BranchDto[] branchesArray = response.getBody();
//
//        if (branchesArray == null) return Collections.emptyList();
//
//        return Arrays.asList(branchesArray);
//    }


//    private List<GithubRepositoryDto> getUserRepositories(String username) {
//        String url = GITHUB_API + "/users/" + username + "/repos";
//
//        ResponseEntity<GithubRepositoryDto[]> response;
//        try {
//            response = restTemplate.getForEntity(url, GithubRepositoryDto[].class);
//            GithubRepositoryDto[] reposArray = response.getBody();
//            if (reposArray == null) return Collections.emptyList();
//            return Arrays.asList(reposArray);
//        } catch (HttpClientErrorException.NotFound e) {
//            throw new UserNotFoundException("User '" + username + "' not found.");
//        }
//    }

}