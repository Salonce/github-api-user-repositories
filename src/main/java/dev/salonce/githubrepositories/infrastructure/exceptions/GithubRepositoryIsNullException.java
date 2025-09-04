package dev.salonce.githubrepositories.infrastructure.exceptions;

public class GithubRepositoryIsNullException extends RuntimeException {
    public GithubRepositoryIsNullException () {
        super("Error. Github repository returned a null value.");
    }
}
