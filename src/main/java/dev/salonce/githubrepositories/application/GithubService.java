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
        List<GithubRepositoryDto> userGithubRepos = githubRestClient.getUserRepositories(username);

        List<GithubRepository> githubRepositoryList = new ArrayList<>();
        for(GithubRepositoryDto githubRepositoryDto : userGithubRepos){
            if (githubRepositoryDto.fork()) continue;

            String repoName = githubRepositoryDto.name();
            String ownerLogin = githubRepositoryDto.ownerDto().login();

            List<Branch> branches = getBranches(username, repoName);

            GithubRepository githubRepository = new GithubRepository(repoName, ownerLogin, branches);
            githubRepositoryList.add(githubRepository);
        }
        return githubRepositoryList;
    }

    public List<Branch> getBranches(String username, String repoName) {
        return githubRestClient.getBranches(username, repoName).stream()
                .map(branchDto -> new Branch(branchDto.name(), branchDto.commitDto().sha()))
                .toList();
    }
}