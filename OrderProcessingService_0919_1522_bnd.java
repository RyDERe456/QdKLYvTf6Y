// 代码生成时间: 2025-09-19 15:22:04
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderProcessingService {
    
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private NotificationService notificationService;
    
    // Process the order
    public ResponseEntity<?> processOrder(Order order) {
        try {
            // Validate the order details
            if (order == null || order.getCustomerId() == null || order.getItems() == null) {
                throw new IllegalArgumentException("Order details are incomplete");
            }
            
            // Save the order to the database
            orderRepository.save(order);
            
            // Process payment
            boolean paymentSuccess = paymentService.processPayment(order);
            if (!paymentSuccess) {
                throw new RuntimeException("Payment processing failed");
            }
            
            // Send order confirmation
            notificationService.sendOrderConfirmation(order);
            
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Exception handler for order processing exceptions
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException e) {
        return "Order processing failed due to an internal error: " + e.getMessage();
    }
    
    // Exception handler for payment processing exceptions
    @ExceptionHandler(PaymentProcessingException.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public String handlePaymentProcessingException(PaymentProcessingException e) {
        return "Payment processing failed: " + e.getMessage();
    }
}
