// 代码生成时间: 2025-08-04 18:50:27
package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/charts")
public class InteractiveChartGeneratorController {

    private final ChartService chartService;

    public InteractiveChartGeneratorController(ChartService chartService) {
        this.chartService = chartService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateChart(@Valid @RequestBody ChartRequest chartRequest) {
        try {
            String chartImage = chartService.generateChart(chartRequest);
            return ResponseEntity.ok(chartImage);
        } catch (ChartGenerationException e) {
            return ResponseEntity.badRequest().body("Failed to generate chart: " + e.getMessage());
        }
    }

    @ExceptionHandler(ChartGenerationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleChartGenerationException(ChartGenerationException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Chart generation failed: " + ex.getMessage());
        return errorResponse;
    }

    // Additional methods for chart service
    // ...
}

class ChartGenerationException extends RuntimeException {
    public ChartGenerationException(String message) {
        super(message);
    }
}

// ChartService is a hypothetical service class that handles chart generation logic
class ChartService {
    public String generateChart(ChartRequest chartRequest) throws ChartGenerationException {
        // Chart generation logic
        return "Generated chart image";
    }
}

// ChartRequest is a DTO class representing the chart request
class ChartRequest {
    // Chart request fields
    // ...
}
