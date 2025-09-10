package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.domain.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
        String outputArtifactId;
        JobStatus jobStatus;
        String message;
}
