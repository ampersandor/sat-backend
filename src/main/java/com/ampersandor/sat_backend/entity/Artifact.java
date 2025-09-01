package com.ampersandor.sat_backend.entity;

import com.ampersandor.sat_backend.domain.ArtifactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "artifact")
public class Artifact {
    @Id
    private String id;
    @Field("filename")
    private String filename;
    @Field("directory")
    private String directory;
    @Field("created_at")
    private LocalDateTime createdAt;
    @Field("size")
    private Long size;
    @Field("type")
    private ArtifactType type;
}
