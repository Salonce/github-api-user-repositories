package dev.salonce.githubrepositories.application;

import dev.salonce.githubrepositories.presentation.dtos.Branch;
import dev.salonce.githubrepositories.presentation.dtos.GithubRepository;
import dev.salonce.githubrepositories.infrastructure.GithubRestClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {

    public final GithubRestClient githubRestClient;
    public final GithubMapper githubMapper;

    public GithubService(GithubRestClient githubRestClient, GithubMapper githubMapper){
        this.githubRestClient = githubRestClient;
        this.githubMapper = githubMapper;
    }

    public List<GithubRepository> getRepositoriesInformation(String username){
        return githubRestClient.getRepositoriesDtos(username).stream()
                .filter(repoDto -> !repoDto.fork())
                .map(repoDto -> githubMapper.mapToGithubRepository(repoDto, getBranches(username, repoDto.name())))
                .toList();
    }

    private List<Branch> getBranches(String username, String repoName) {
        return githubRestClient.getBranchDtos(username, repoName).stream()
                .map(githubMapper::mapToBranch)
                .toList();
    }
}