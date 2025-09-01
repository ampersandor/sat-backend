package com.amperandor.sat_backend.service;

import com.amperandor.sat_backend.entity.Artifact;
import com.amperandor.sat_backend.repository.ArtifactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {
    private final ArtifactRepository artifactRepository;

    public Mono<Artifact> saveArtifact(Artifact artifact) {
        return artifactRepository.save(artifact)
                .doOnError(error -> log.error("something went wrong in mongodb", error))
                .doOnSuccess(data -> log.info("successfully saved in mongodb, document: {}", data.getId()));
    }


}
