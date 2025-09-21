// 代码生成时间: 2025-09-21 17:28:03
package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class SortingService {

    public List<Integer> sortNumbers(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("The list of numbers cannot be null or empty.");
        }
        // Sorts the list of numbers using the Arrays.sort method
        numbers.sort(Integer::compareTo);
        return numbers;
    }
}
