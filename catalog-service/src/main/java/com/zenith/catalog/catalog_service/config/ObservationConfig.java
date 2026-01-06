package com.zenith.catalog.catalog_service.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservationConfig {

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    @Bean
    public ZipkinSpanExporter zipkinSpanExporter() {
        // Points to your Docker Zipkin container
        return ZipkinSpanExporter.builder()
                .setEndpoint("http://localhost:9411/api/v2/spans")
                .build();
    }

}