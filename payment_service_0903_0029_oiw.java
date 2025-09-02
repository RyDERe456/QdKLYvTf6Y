// 代码生成时间: 2025-09-03 00:29:54
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

// 定义支付服务组件
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    // 自动注入PaymentRepository
    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 处理支付请求
    @Transactional
    public Payment processPayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }

        // 检查支付请求是否已存在
        Optional<Payment> existingPayment = paymentRepository.findByRequestId(paymentRequest.getRequestId());
        if (existingPayment.isPresent()) {
            throw new PaymentException("Payment request already exists");
        }

        // 创建新的支付记录
        Payment payment = new Payment();
        payment.setRequestId(paymentRequest.getRequestId());
        payment.setAmount(paymentRequest.getAmount());

        // 保存支付记录
        return paymentRepository.save(payment);
    }

    // 异常处理器
    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePaymentException(PaymentException ex) {
        return ex.getMessage();
    }
}

// 支付请求类
class PaymentRequest {
    private String requestId;
    private double amount;

    // 省略getter和setter...
}

// 支付实体类
class Payment {
    private String requestId;
    private double amount;

    // 省略getter和setter...
}

// 支付异常类
class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}

// 支付仓库接口
interface PaymentRepository {
    Optional<Payment> findByRequestId(String requestId);
    Payment save(Payment payment);
}
