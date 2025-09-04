package dev.salonce.githubrepositories.application;

import dev.salonce.githubrepositories.infrastructure.dtos.BranchDto;
import dev.salonce.githubrepositories.infrastructure.dtos.GithubRepositoryDto;
import dev.salonce.githubrepositories.domain.Branch;
import dev.salonce.githubrepositories.domain.GithubRepository;
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

            List<BranchDto> branchDtos = githubRestClient.getBranches(username, repoName);

            List<Branch> branches = new ArrayList<>();
            for (BranchDto branchDto : branchDtos){
                branches.add(new Branch(branchDto.name(), branchDto.commitDto().sha()));
            }

            GithubRepository githubRepository = new GithubRepository(repoName, ownerLogin, branches);
            githubRepositoryList.add(githubRepository);
        }
        return githubRepositoryList;
    }
}