package dev.salonce.githubrepositories.infrastructure;

public class NullDataException extends RuntimeException {
    public NullDataException (String message) {
        super(message);
    }
}
