package com.amperandor.sat_backend.repository;

import com.amperandor.sat_backend.entity.Artifact;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ArtifactRepository extends ReactiveMongoRepository<Artifact, String> {
}
