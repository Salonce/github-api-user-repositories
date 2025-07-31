package dev.salonce.atiperatask.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryDto {

    private String name;

    @JsonProperty("owner")
    private OwnerDto owner;

    @JsonProperty("branches_url")
    private String branchesUrl;

    @JsonProperty("fork")
    private Boolean fork;

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

    public Boolean getFork() {
        return fork;
    }
}