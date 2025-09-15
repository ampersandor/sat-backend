package com.ampersandor.sat_backend.service;

import com.ampersandor.sat_backend.domain.ArtifactType;
import com.ampersandor.sat_backend.dto.ArtifactDto;
import com.ampersandor.sat_backend.dto.ArtifactRequest;
import com.ampersandor.sat_backend.entity.Artifact;
import com.ampersandor.sat_backend.exceptions.ApplicationExceptions;
import com.ampersandor.sat_backend.mapper.ArtifactMapper;
import com.ampersandor.sat_backend.repository.ArtifactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final FileService fileService;

   public Mono<Void> deleteFile(String artifactId) {
       return artifactRepository.findById(artifactId)
                .switchIfEmpty(ApplicationExceptions.artifactsNotFound(artifactId))
                .flatMap(artifact -> {
                    return fileService.deleteFile(artifact.getDirectory(), artifact.getFilename())
                            .then(artifactRepository.delete(artifact));
                });
    }
   

    public Mono<ArtifactDto> saveInputFile(FilePart filePart, Long fileSize) {
        return fileService.saveFile(filePart, fileSize, ArtifactType.INPUT)
                .map(ArtifactMapper::toEntity)
                .flatMap(this::saveArtifact)
                .map(ArtifactMapper::toDto);
    }

    public Mono<Map<ArtifactType, ArtifactDto>> saveOutputFiles(String alignFile, String statFile, String directory) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Flux.just(Tuples.of(alignFile, ArtifactType.ALIGNED), Tuples.of(statFile, ArtifactType.STAT))
                .map(tuple -> new ArtifactRequest(tuple.getT1(), directory, now, 0L, tuple.getT2()))
                .flatMap(this::saveOutputFile)
                .collectMap(ArtifactDto::artifactType);
    }

    public Mono<ArtifactDto> saveOutputFile(ArtifactRequest artifactRequest) {
        return artifactRepository
                .save(ArtifactMapper.toEntity(artifactRequest))
                .flatMap(this::saveArtifact)
                .map(ArtifactMapper::toDto);
    }

    public Mono<Artifact> saveArtifact(Artifact artifact) {
        return artifactRepository.save(artifact)
                .doOnError(error -> log.error("something went wrong in mongodb", error))
                .doOnSuccess(data -> log.info("successfully saved in mongodb, document: {}", data.getId()));
    }

    public Mono<File> getFile(String artifactId) {
        return getArtifact(artifactId)
                .map(artifactDto -> fileService.getFile(artifactDto.directory(), artifactDto.filename()));
    }

    public Mono<ArtifactDto> getArtifact(String artifactId) {
        return artifactRepository.findById(artifactId)
                .switchIfEmpty(ApplicationExceptions.artifactsNotFound(artifactId))
                .map(ArtifactMapper::toDto);
    }

    public Flux<ArtifactDto> getArtifactByType(ArtifactType artifactType) {
        return artifactRepository.findAllByType(artifactType)
                .map(ArtifactMapper::toDto);
    }




}
