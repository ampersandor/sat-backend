package com.amperandor.sat_backend.service;

import com.amperandor.sat_backend.domain.ArtifactType;
import com.amperandor.sat_backend.dto.ArtifactDto;
import com.amperandor.sat_backend.entity.Artifact;
import com.amperandor.sat_backend.mapper.ArtifactMapper;
import com.amperandor.sat_backend.repository.ArtifactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final FileService fileService;

    public Mono<ArtifactDto> saveInputFile(FilePart filePart, Long fileSize) {
        return fileService.saveFile(filePart, fileSize, ArtifactType.INPUT)
                .map(ArtifactMapper::toEntity)
                .flatMap(this::saveArtifact)
                .map(ArtifactMapper::toDto);
    }
    public Mono<Artifact> saveArtifact(Artifact artifact) {
        return artifactRepository.save(artifact)
                .doOnError(error -> log.error("something went wrong in mongodb", error))
                .doOnSuccess(data -> log.info("successfully saved in mongodb, document: {}", data.getId()));
    }



}
