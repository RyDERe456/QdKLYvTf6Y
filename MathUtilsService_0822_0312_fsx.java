// 代码生成时间: 2025-08-22 03:12:45
 * @author [Your Name]
 * @version 1.0
 * @since [Date]
 */

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MathUtilsService {

    /**<ol>
     * Calculates the sum of two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The sum of the two numbers.
     * @throws IllegalArgumentException If either number is null.
     */
    public double add(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid input values. Numbers cannot be NaN.");
        }
        return a + b;
    }

    /**<ol>
     * Calculates the difference between two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The difference between the two numbers.
     * @throws IllegalArgumentException If either number is null.
     */
    public double subtract(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid input values. Numbers cannot be NaN.");
        }
        return a - b;
    }

    /**<ol>
     * Calculates the product of two numbers.
     *
     * @param a The first number.
     * @param b The second number.
     * @return The product of the two numbers.
     * @throws IllegalArgumentException If either number is null.
     */
    public double multiply(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid input values. Numbers cannot be NaN.");
        }
        return a * b;
    }

    /**<ol>
     * Calculates the division of two numbers.
     *
     * @param a The first number (dividend).
     * @param b The second number (divisor).
     * @return The division of the two numbers.
     * @throws IllegalArgumentException If the divisor is zero or if either number is NaN.
     */
    public double divide(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid input values. Numbers cannot be NaN.");
        }
        if (b == 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Division by zero is not allowed.");
        }
        return a / b;
    }

    /**<ol>
     * Calculates the power of a number to a certain exponent.
     *
     * @param base The base number.
     * @param exponent The exponent to which the base number is raised.
     * @return The result of the power operation.
     * @throws IllegalArgumentException If the base or exponent is NaN.
     */
    public double power(double base, double exponent) {
        if (Double.isNaN(base) || Double.isNaN(exponent)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Invalid input values. Numbers cannot be NaN.");
        }
        return Math.pow(base, exponent);
    }
}
