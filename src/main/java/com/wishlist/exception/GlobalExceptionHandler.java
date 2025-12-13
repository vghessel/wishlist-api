package com.wishlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<?> handleWishlistNotFound(WishlistNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(WishlistLimitExceededException.class)
    public ResponseEntity<?> handleLimitExceeded(WishlistLimitExceededException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error(
                        HttpStatus.CONFLICT,
                        ex.getMessage()
                ));
    }

    private Map<String, Object> error(HttpStatus status, String message) {
        return Map.of(
                "timestamp", Instant.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }
}
