package dev.salonce.githubrepositories.infrastructure;

public class GithubBranchIsNullException extends RuntimeException {
    public GithubBranchIsNullException (String message) {
        super(message);
    }
}
