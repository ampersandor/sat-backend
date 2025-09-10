package com.ampersandor.sat_backend.controller;

import com.ampersandor.sat_backend.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public Mono<HealthResponse> checkHealth() {
        return Mono.just(new HealthResponse("UP", LocalDateTime.now(), "Thanks for asking :)"));
    }

}
