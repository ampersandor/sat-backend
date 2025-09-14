package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.entity.Statistic;
import com.fasterxml.jackson.annotation.JsonProperty;

public record JobUpdateRequest(
        @JsonProperty("task_id") String taskId,
        @JsonProperty("status") JobStatus status,
        @JsonProperty("align_file") String alignFile,
        @JsonProperty("stat_file") String statFile,
        @JsonProperty("statistic") Statistic statistic,
        @JsonProperty("output_dir") String outputDir,
        @JsonProperty("message") String message
) {
}

