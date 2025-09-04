package dev.salonce.githubrepositories.application;

import dev.salonce.githubrepositories.infrastructure.dtos.BranchDto;
import dev.salonce.githubrepositories.infrastructure.dtos.GithubRepositoryDto;
import dev.salonce.githubrepositories.presentation.dtos.Branch;
import dev.salonce.githubrepositories.presentation.dtos.GithubRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GithubMapper {
    public GithubRepository mapToGithubRepository(GithubRepositoryDto dto, List<Branch> branches) {
        return new GithubRepository(dto.name(), dto.ownerDto().login(), branches);
    }

    public Branch mapToBranch(BranchDto branchDto) {
        return new Branch(branchDto.name(), branchDto.commitDto().sha());
    }
}
