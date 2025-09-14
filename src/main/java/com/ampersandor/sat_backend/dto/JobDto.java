package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.domain.Tool;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobDto {
        String id;
        String taskId;
        String inputArtifactId;
        String baseName;
        String dirName;
        Tool tool;
        String options;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        String alignArtifactId = "";
        String statArtifactId = "";
        JobStatus jobStatus;
        String message = "";
}
