package dev.salonce.atiperatask;

public class Branch {

    public Branch(String name, String sha){
        this.name = name;
        this.sha = sha;
    }

    private String name;
    private String sha;

    public String getName() {
        return name;
    }

    public String getSha() {
        return sha;
    }
}
