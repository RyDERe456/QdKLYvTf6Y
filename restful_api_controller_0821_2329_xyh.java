// 代码生成时间: 2025-08-21 23:29:14
package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestfulApiController {

    // 模拟数据库存储
    private final Map<String, String> dataStore = new HashMap<>();

    // GET请求，返回存储的数据
    @GetMapping("/data")
    public ResponseEntity<Map<String, String>> getData() {
        return ResponseEntity.ok(dataStore);
    }

    // POST请求，添加或更新数据
    @PostMapping("/data")
    public ResponseEntity<String> postData(@RequestBody Map<String, String> payload) {
        String key = payload.keySet().iterator().next();
        String value = payload.get(key);
        dataStore.put(key, value);
        return ResponseEntity.ok("Data added/updated successfully");
    }

    // 错误处理器
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", e.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
