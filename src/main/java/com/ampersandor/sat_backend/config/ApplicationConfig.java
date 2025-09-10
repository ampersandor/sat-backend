package com.ampersandor.sat_backend.config;

import com.ampersandor.sat_backend.dto.JobDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Slf4j
@Configuration
public class ApplicationConfig {

    @Bean
    public Sinks.Many<JobDto> jobSink() {
        return Sinks.many().multicast().onBackpressureBuffer();
    }
}
