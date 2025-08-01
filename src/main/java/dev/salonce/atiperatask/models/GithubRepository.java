package dev.salonce.atiperatask.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GithubRepository {
    private final String name;

    public GithubRepository(String name, String ownerLogin, List<Branch> branches) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

    @JsonProperty("owner_login")
    private String ownerLogin;
    private List<Branch> branches;

    public String getName() {
        return name;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
