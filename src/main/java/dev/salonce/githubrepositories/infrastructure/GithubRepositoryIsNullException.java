package dev.salonce.githubrepositories.infrastructure;

public class GithubRepositoryIsNullException extends RuntimeException {
    public GithubRepositoryIsNullException (String message) {
        super(message);
    }
}
