package com.ampersandor.sat_backend.dto;

import java.time.LocalDateTime;

public record HealthResponse(String status,
                             LocalDateTime timestamp,
                             String details) {
}