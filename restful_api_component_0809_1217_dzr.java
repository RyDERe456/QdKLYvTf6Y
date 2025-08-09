// 代码生成时间: 2025-08-09 12:17:19
package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class RestfulApiComponent {

    // Endpoint to return a greeting message
    @GetMapping("/greeting")
    public ResponseEntity<String> getGreeting() {
        try {
            // Simulate some business logic
            String message = "Hello, this is a RESTful API!";
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            // Handle unexpected exceptions with a 500 status code
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", e);
        }
    }

    // Error handling method
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
