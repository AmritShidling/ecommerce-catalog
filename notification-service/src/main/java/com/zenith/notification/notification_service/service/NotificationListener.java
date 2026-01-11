package com.zenith.notification.notification_service.service;

import com.zenith.notification.notification_service.DTO.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationListener {
    @KafkaListener(topics = "order-placed-events", groupId = "notification-group")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        log.info("----NOTIFICATION SERVICE----");
        log.info("Sending email to User ID: {}", orderPlacedEvent.userId());
        log.info("Message: your Order {} has been placed successfully!", orderPlacedEvent.orderNumber());
        log.info("Items being placed: {}", orderPlacedEvent.orderLineItems().size());
        log.info("----------------------------");
    }
}
