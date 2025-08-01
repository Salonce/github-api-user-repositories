package dev.salonce.atiperatask;

import java.util.List;

public class Repository {
    private String name;

    public Repository(String name, String ownerLogin, List<Branch> branches) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }

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
