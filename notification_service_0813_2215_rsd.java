// 代码生成时间: 2025-08-13 22:15:35
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.kafka.support.ProducerFailureHandler;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.kafka.support.ProducerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutionException;

@Service
@EnableRetry
public class NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private RetryTemplate retryTemplate;

    // 发送消息到Kafka主题
    public void sendNotification(String message, String topic) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(result -> {
            if (result != null && result.getRecordMetadata() != null) {
                LOGGER.info("Message sent to topic {} with offset {}", topic, result.getRecordMetadata().offset());
            }
        }, ex -> {
            LOGGER.error("Failed to send message to topic {}: {}", topic, ex.getMessage());
        });
    }

    // 配置Kafka模板的失败处理
    public void configureProducerFailureHandler() {
        kafkaTemplate.setProducerFailureHandler(new ProducerFailureHandler() {
            @Override
            public void handleException(Exception exception) {
                LOGGER.error("Producer Failure: ", exception);
            }
        });
    }

    // 监听Kafka消息并处理
    @ProducerListener(topics = "notificationTopic")
    public void handleNotificationMessage(String message) {
        try {
            // 模拟消息处理逻辑
            Thread.sleep(1000); // 模拟耗时操作
            LOGGER.info("Received notification: {}", message);
        } catch (InterruptedException e) {
            LOGGER.error("Error while handling notification message: ", e);
        }
    }

    // 异常处理
    public void handleError(Exception exception) {
        LOGGER.error("An error occurred: ", exception);
        // 可以在这里添加更多的错误处理逻辑
    }
}
