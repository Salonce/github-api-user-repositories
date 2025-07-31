package dev.salonce.atiperatask;

import java.util.List;

public class RepositoryInformation {
    private String name;

    public RepositoryInformation(String name, String owner_login, List<Branch> branches) {
        this.name = name;
        this.owner_login = owner_login;
        this.branches = branches;
    }

    private String owner_login;
    private List<Branch> branches;

    public String getName() {
        return name;
    }

    public String getOwner_login() {
        return owner_login;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
