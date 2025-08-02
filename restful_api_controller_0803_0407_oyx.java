// 代码生成时间: 2025-08-03 04:07:27
package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class RestfulApiController {

    // RESTful API endpoint for GET request
    @GetMapping("/items/{id}")
    public ResponseEntity<String> getItemById(@PathVariable String id) {
        try {
            // Simulate getting an item by ID
            String item = "Item with ID: " + id;
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            // Handle exceptions and return error response
            return new ResponseEntity<>("Error retrieving item: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // RESTful API endpoint for POST request
    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody String item) {
        try {
            // Simulate creating an item
            return ResponseEntity.status(HttpStatus.CREATED).body("Item created with content: " + item);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating item", e);
        }
    }

    // RESTful API endpoint for PUT request
    @PutMapping("/items/{id}")
    public ResponseEntity<String> updateItem(@PathVariable String id, @RequestBody String item) {
        try {
            // Simulate updating an item
            return ResponseEntity.ok("Item with ID: " + id + " updated with content: " + item);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating item", e);
        }
    }

    // RESTful API endpoint for DELETE request
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        try {
            // Simulate deleting an item
            return ResponseEntity.ok("Item with ID: " + id + " deleted");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting item", e);
        }
    }
}
