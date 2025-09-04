package dev.salonce.githubrepositories.infrastructure.exceptions;

public class GithubBranchIsNullException extends RuntimeException {
    public GithubBranchIsNullException () {
        super("Error. One of the repository branches returned a null value.");
    }
}
