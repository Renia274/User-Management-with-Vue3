package com.example.vue.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Custom error controller for handling different types of errors globally.
 */
@ControllerAdvice
@RestController
@RequestMapping("/api/v")
public class CustomErrorController implements ErrorController {

    /**
     * Handles general exceptions thrown in the application.
     *
     * @param e The exception that was thrown.
     * @return A ResponseEntity containing the error message and HTTP status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + e.getMessage());
    }

    /**
     * Handles errors by checking the status code and providing appropriate responses.
     *
     * @param request The HttpServletRequest object.
     * @return A ResponseEntity containing the error message and HTTP status code.
     */
    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error 404: Resource not found");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error 500: Internal server error");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("An unexpected error occurred");
    }
}
