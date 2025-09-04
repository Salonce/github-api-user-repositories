package dev.salonce.githubrepositories.presentation;

import dev.salonce.githubrepositories.infrastructure.GithubBranchIsNullException;
import dev.salonce.githubrepositories.infrastructure.GithubRepositoryIsNullException;
import dev.salonce.githubrepositories.infrastructure.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(GithubRepositoryIsNullException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(GithubRepositoryIsNullException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Error. One of github repositories returned null value."),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(GithubBranchIsNullException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(GithubBranchIsNullException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Error. One of github branches returned a null value."),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralError(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
