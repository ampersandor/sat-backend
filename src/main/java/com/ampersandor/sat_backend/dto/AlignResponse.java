package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AlignResponse(
    @JsonProperty("status") JobStatus jobStatus,
    @JsonProperty("task_id") String taskId
){}
