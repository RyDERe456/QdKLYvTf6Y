// 代码生成时间: 2025-10-07 02:47:22
package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/tabs")
public class TabSwitcherController {

    // GET endpoint to switch tabs based on the provided tabId
    @GetMapping("/switch")
    public ResponseEntity<String> switchTab(
            @RequestParam("tabId") @NotNull String tabId) {
        // Simulate tab switching logic
        return ResponseEntity.ok("Switched to tab: " + tabId);
    }

    // Error handling for HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Invalid request: Please provide a valid tabId.");
    }

    // Generic error handling for any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An error occurred: " + ex.getMessage());
    }
}
