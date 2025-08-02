package dev.salonce.atiperatask.exceptions;

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

    @ExceptionHandler(NullDataException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(NullDataException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
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
