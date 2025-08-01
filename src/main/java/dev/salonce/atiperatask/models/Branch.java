package dev.salonce.atiperatask.models;

public class Branch {

    public Branch(String name, String sha){
        this.name = name;
        this.sha = sha;
    }

    private final String name;
    private final String sha;

    public String getName() {
        return name;
    }

    public String getSha() {
        return sha;
    }
}
