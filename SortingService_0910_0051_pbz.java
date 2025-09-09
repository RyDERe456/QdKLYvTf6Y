// 代码生成时间: 2025-09-10 00:51:40
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortingService {

    private static final String UNSUPPORTED_OPERATION_ERROR_MSG = "Unsupported operation, only ascending and descending order are supported";

    public List<Integer> sortList(List<Integer> list, String order) {
        try {
            if (order == null || (!order.equalsIgnoreCase("ascending") && !order.equalsIgnoreCase("descending"))) {
                throw new IllegalArgumentException(UNSUPPORTED_OPERATION_ERROR_MSG);
            }
            Collections.sort(list);
            if ("descending".equalsIgnoreCase(order)) {
                Collections.reverse(list);
            }
            return list;
        } catch (IllegalArgumentException e) {
            // Log the exception (logging framework should be configured)
            // In a real-world scenario, you would use a logger like SLF4J
            System.err.println("Error: " + e.getMessage());
            // Depending on the use case, you might throw the exception further or handle it some other way
            throw e;
        }
    }

    public List<String> sortStringList(List<String> list, String order) {
        try {
            if (order == null || (!order.equalsIgnoreCase("ascending") && !order.equalsIgnoreCase("descending"))) {
                throw new IllegalArgumentException(UNSUPPORTED_OPERATION_ERROR_MSG);
            }
            list.sort(String.CASE_INSENSITIVE_ORDER);
            if ("descending".equalsIgnoreCase(order)) {
                list = list.stream()
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .collect(Collectors.toList())
                    .stream()
                    .reduce(list.size(), (i, j) -> list.get(list.size() - 1 - i), (i1, i2) -> list.get(list.size() - 1 - Integer.min(i1, i2)));
            }
            return list;
        } catch (IllegalArgumentException e) {
            // Log the exception
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }
}