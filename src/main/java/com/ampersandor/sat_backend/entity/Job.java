package com.ampersandor.sat_backend.entity;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.domain.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "align_job")
public class Job {
    @Id
    private String id;
    @Field("task_id")
    private String taskId;
    @Field("input_artifact_id")
    private String inputArtifactId;
    @Field("base_name")
    private String baseName;
    @Field("dir_name")
    private String dirName;
    @Field("align_tool")
    private Tool tool;
    @Field("options")
    private String options;
    @Field("created_at")
    private LocalDateTime createdAt;
    @Field("updated_at")
    private LocalDateTime updatedAt;
    @Field("output_artifact_id")
    private String outputArtifactId;
    @Field("align_job_status")
    private JobStatus jobStatus;
    @Field("message")
    private String message;
}
