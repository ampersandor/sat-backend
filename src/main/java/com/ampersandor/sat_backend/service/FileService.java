package com.ampersandor.sat_backend.service;

import com.ampersandor.sat_backend.domain.ArtifactType;
import com.ampersandor.sat_backend.dto.ArtifactRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Service
public class FileService {

    @Value("${sat.data.prefix}")
    private String prefix;

    private static final DateTimeFormatter DIR_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm");

    public File getFile(String directory, String filename) {
        Path filePath = Paths.get(prefix).resolve(directory).resolve(filename);
        return new File(filePath.toString());

    }
    public Mono<ArtifactRequest> saveFile(FilePart filePart, Long fileSize, ArtifactType artifactType) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));;
        String directory = buildDirectory(now);
        String filename = safeFilename(filePart.filename());

        ArtifactRequest artifact = new ArtifactRequest(
                filename,
                directory,
                now,
                fileSize,
                artifactType
        );

        Path dir = Paths.get(prefix).resolve(directory).normalize();
        Path dest = dir.resolve(filename).normalize();

        return createDirectory(dir)
                .then(filePart.transferTo(dest))
                .thenReturn(artifact);
    }

    private Mono<Void> createDirectory(Path directory) {
        return Mono.fromRunnable(() -> {  // Runnable 은 반환 값이 없을 때
                    try {
                        Files.createDirectories(directory);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create directory: " + directory, e);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    private String buildDirectory(LocalDateTime now) {
        return DIR_FORMATTER.format(now) + "/" + UUID.randomUUID();
    }

    private String safeFilename(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}