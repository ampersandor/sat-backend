package com.ampersandor.sat_backend.repository;

import com.ampersandor.sat_backend.domain.ArtifactType;
import com.ampersandor.sat_backend.entity.Artifact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ArtifactRepository extends ReactiveMongoRepository<Artifact, String> {
    Flux<Artifact> findAllByType(ArtifactType artifactType);
}
