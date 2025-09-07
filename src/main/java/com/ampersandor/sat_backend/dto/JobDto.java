package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.domain.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
        String id;
        String taskId;
        String inputArtifactId;
        String inputPath;
        Tool tool;
        String options;
        String createdAt;
        String updatedAt;
        String outputArtifactId;
        JobStatus jobStatus;
        String message;
}
