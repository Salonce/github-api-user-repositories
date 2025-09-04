package dev.salonce.githubrepositories.application;

import dev.salonce.githubrepositories.infrastructure.dtos.BranchDto;
import dev.salonce.githubrepositories.infrastructure.dtos.GithubRepositoryDto;
import dev.salonce.githubrepositories.presentation.dtos.Branch;
import dev.salonce.githubrepositories.presentation.dtos.GithubRepository;
import dev.salonce.githubrepositories.infrastructure.GithubRestClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubService {

    public final GithubRestClient githubRestClient;

    public GithubService(GithubRestClient githubRestClient){
        this.githubRestClient = githubRestClient;
    }

    public List<GithubRepository> getRepositoriesInformation(String username){
        return githubRestClient.getUserRepositories(username).stream()
                .filter(repoDto -> !repoDto.fork())
                .map(repoDto -> new GithubRepository(repoDto.name(), repoDto.ownerDto().login(), getBranches(username, repoDto.name())))
                .toList();
    }

    private List<Branch> getBranches(String username, String repoName) {
        return githubRestClient.getBranches(username, repoName).stream()
                .map(branchDto -> new Branch(branchDto.name(), branchDto.commitDto().sha()))
                .toList();
    }
}