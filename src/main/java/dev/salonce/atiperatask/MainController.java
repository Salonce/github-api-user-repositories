package dev.salonce.atiperatask;

import dev.salonce.atiperatask.Dtos.GithubRepositoryDto;
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

    @GetMapping("/user/{username}/repositories")
    public List<RepositoryInformation> getNonForkRepositories(@PathVariable String username) {
        return githubService.getRepositoriesInformation(username);
    }
}
