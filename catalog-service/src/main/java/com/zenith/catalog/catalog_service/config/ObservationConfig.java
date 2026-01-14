package com.zenith.catalog.catalog_service.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.opentelemetry.exporter.zipkin.ZipkinSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservationConfig {

    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    @Value("${management.zipkin.tracing.endpoint:http://localhost:9411/api/v2/spans}")
    private String zipkinEndpoint;

    @Bean
    public ZipkinSpanExporter zipkinSpanExporter() {
        return ZipkinSpanExporter.builder()
                .setEndpoint(zipkinEndpoint) // Uses the injected variable
                .build();
    }

}