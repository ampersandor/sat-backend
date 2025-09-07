package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.Tool;

public record JobRequest(
    Tool tool,
    String options
) {
}
