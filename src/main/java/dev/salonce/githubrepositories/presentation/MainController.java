package dev.salonce.githubrepositories.presentation;

import dev.salonce.githubrepositories.presentation.dtos.GithubRepository;
import dev.salonce.githubrepositories.application.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {
    private final GithubService githubService;

    public MainController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/users/{username}/repositories")
    public List<GithubRepository> getNonForkRepositories(@PathVariable String username) {
        return githubService.getRepositoriesInformation(username);
    }
}
