package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JobUpdateRequest(
        @JsonProperty("task_id") String taskId,
        @JsonProperty("status") JobStatus status,
        @JsonProperty("output_file") String outputFile,
        @JsonProperty("output_dir") String outputDir,
        @JsonProperty("message") String message
) {
}

