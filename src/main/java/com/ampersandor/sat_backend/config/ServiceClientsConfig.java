package com.ampersandor.sat_backend.config;

import com.ampersandor.sat_backend.client.AlignmentServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ServiceClientsConfig {
    private static final int MAX = 10 * 1024 * 1024; // 10MB
    @Bean
    public AlignmentServiceClient alignmentServiceClient(@Value("${sat.core.url}") String baseUrl){
        return new AlignmentServiceClient(createWebClient(baseUrl));
    }

    private WebClient createWebClient(String baseUrl){
        return WebClient.builder()
                        .baseUrl(baseUrl)
                        .defaultHeader("Content-Type", "application/json")
                        .defaultHeader("Accept", "application/json")
                        .filter((request, next) -> next.exchange(request))
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(MAX))
                        .build();
    }
}
