package com.zenith.order.order_service.config;

import com.zenith.order.order_service.DTO.InventoryResultEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, InventoryResultEvent> consumerFactory(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "inventory-result-group");

        JsonDeserializer<InventoryResultEvent> jsonDeserializer = new JsonDeserializer<>(InventoryResultEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(false);
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryResultEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryResultEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
