// 代码生成时间: 2025-10-01 01:41:19
import org.springframework.stereotype.Service;
# 优化算法效率
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
# 改进用户体验
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
# FIXME: 处理边界情况

/**
 * TaxCalculationService provides tax calculation functionalities.
 */
@Service
public class TaxCalculationService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.20"); // 20% tax rate

    /**
     * Calculates the tax amount based on the income.
     * 
     * @param income the income amount
     * @return the calculated tax amount
# 改进用户体验
     */
# TODO: 优化性能
    public BigDecimal calculateTax(BigDecimal income) {
        if (income == null || income.compareTo(BigDecimal.ZERO) < 0) {
# 添加错误处理
            throw new IllegalArgumentException("Income cannot be null or negative");
        }
# TODO: 优化性能
        return income.multiply(TAX_RATE);
    }
# 改进用户体验
}

/**
 * TaxCalculationController provides REST endpoints for tax calculation.
 */
@RestController
public class TaxCalculationController {

    @Autowired
# 改进用户体验
    private TaxCalculationService taxCalculationService;
# NOTE: 重要实现细节

    @GetMapping("/calculateTax")
    public BigDecimal calculateTax(@RequestParam BigDecimal income) {
        try {
# 改进用户体验
            return taxCalculationService.calculateTax(income);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}