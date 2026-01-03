package com.zenith.catalog.catalog_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;

import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.http.HttpHeaders;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .withHeaders(() -> {
                    // Notice the specific package for the required type
                    org.springframework.data.elasticsearch.support.HttpHeaders headers =
                            new org.springframework.data.elasticsearch.support.HttpHeaders();

                    headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=8");
                    headers.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=8");
                    return headers;
                })
                .build();
    }
}
