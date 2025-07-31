package dev.salonce.atiperatask;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryDto {

    private String name;

    @JsonProperty("owner")
    private OwnerDto owner;

    @JsonProperty("branches_url")
    private String branchesUrl;

    public GithubRepositoryDto() {}

    public GithubRepositoryDto(String name, OwnerDto owner, String branchesUrl) {
        this.name = name;
        this.owner = owner;
        this.branchesUrl = branchesUrl;
    }

    public String getName() {
        return name;
    }

    public OwnerDto getOwner() {
        return owner;
    }

    public String getBranchesUrl() {
        return branchesUrl;
    }
}