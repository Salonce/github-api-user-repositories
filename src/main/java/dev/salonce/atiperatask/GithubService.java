package dev.salonce.atiperatask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GITHUB_API = "https://api.github.com";

    public List<GithubRepositoryDto> getUserRepositories(String username) {
        String url = GITHUB_API + "/users/" + username + "/repos";

        ResponseEntity<GithubRepositoryDto[]> response;
        try {
            response = restTemplate.getForEntity(url, GithubRepositoryDto[].class);
        } catch (Exception e) {
            throw new UserNotFoundException("User '" + username + "' not found.");
        }
        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new UserNotFoundException("User '" + username + "' not found.");
        }

        GithubRepositoryDto[] reposArray = response.getBody();

        if (reposArray == null) return Collections.emptyList();

        return Arrays.asList(reposArray);
    }

    public List<BranchDto> getBranches(String url) {

        ResponseEntity<BranchDto[]> response;
        try {
            response = restTemplate.getForEntity(url, BranchDto[].class);
        } catch (Exception e) {
            throw new UserNotFoundException("Branches not found.");
        }

        BranchDto[] branchesArray = response.getBody();

        if (branchesArray == null) return Collections.emptyList();

        return Arrays.asList(branchesArray);
    }
}