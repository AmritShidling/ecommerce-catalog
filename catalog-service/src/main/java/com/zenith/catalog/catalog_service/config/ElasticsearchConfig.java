package com.zenith.catalog.catalog_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.http.HttpHeaders;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    // This pulls the URL from your compose file.
    // If not found, it defaults to localhost (for local IDE testing).
    @Value("${spring.elasticsearch.uris:localhost:9200}")
    private String elasticsearchUri;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUri) // <--- CRITICAL CHANGE
                .withHeaders(() -> {
                    org.springframework.data.elasticsearch.support.HttpHeaders headers =
                            new org.springframework.data.elasticsearch.support.HttpHeaders();
                    headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=8");
                    headers.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=8");
                    return headers;
                })
                .build();
    }
}