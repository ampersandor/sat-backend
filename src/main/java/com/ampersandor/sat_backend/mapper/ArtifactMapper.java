package com.ampersandor.sat_backend.mapper;

import com.ampersandor.sat_backend.dto.ArtifactDto;
import com.ampersandor.sat_backend.dto.ArtifactRequest;
import com.ampersandor.sat_backend.entity.Artifact;

public class ArtifactMapper {
    public static Artifact toEntity(ArtifactRequest artifactRequest) {
        return Artifact.builder()
                .filename(artifactRequest.filename())
                .directory(artifactRequest.directory())
                .createdAt(artifactRequest.createdAt())
                .size(artifactRequest.size())
                .type(artifactRequest.artifactType())
                .build();
    }

    public static ArtifactDto toDto(Artifact artifact) {
        return new ArtifactDto(
                artifact.getFilename(),
                artifact.getDirectory(),
                artifact.getCreatedAt(),
                artifact.getSize(),
                artifact.getType()
        );
    }
}
