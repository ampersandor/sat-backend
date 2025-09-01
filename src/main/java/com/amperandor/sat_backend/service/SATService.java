package com.amperandor.sat_backend.service;

import com.amperandor.sat_backend.domain.ArtifactType;
import com.amperandor.sat_backend.dto.ArtifactDto;
import com.amperandor.sat_backend.mapper.ArtifactMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class SATService {
    private final ArtifactService artifactService;
    private final FileService fileService;

    public Mono<ArtifactDto> saveInputFile(FilePart filePart, Long fileSize) {
        return fileService.saveFile(filePart, fileSize, ArtifactType.INPUT)
                .map(ArtifactMapper::toEntity)
                .flatMap(artifactService::saveArtifact)
                .map(ArtifactMapper::toDto);
    }
}
