package com.ampersandor.sat_backend.controller;

import com.ampersandor.sat_backend.advice.Logging;
import com.ampersandor.sat_backend.domain.ArtifactType;
import com.ampersandor.sat_backend.dto.ArtifactDto;
import com.ampersandor.sat_backend.service.ArtifactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@Logging
@RequestMapping("api/v1/sat")
public class ArtifactController {
    private final ArtifactService artifactService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ArtifactDto>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono, ServerHttpRequest request) {
        return filePartMono.flatMap(filePart -> artifactService.saveInputFile(
                        filePart,
                        request.getHeaders().getContentLength()))
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> log.info("successfully uploaded file"))
                .doOnError(error -> log.error("Something went wrong while uploading file", error))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @GetMapping("/download/{artifactId}")
    public Mono<ResponseEntity<Resource>> downloadFile(@PathVariable String artifactId, ServerHttpRequest request) {
        return artifactService.getFile(artifactId)
                .map(file -> {
                    Resource resource = new FileSystemResource(file);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
                    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

                    return ResponseEntity.ok().headers(headers).body(resource);
                });
    }

    @GetMapping("/list/{artifactType}")
    public Flux<ArtifactDto> listFilesByType(@PathVariable ArtifactType artifactType) {
        return artifactService.getArtifactByType(artifactType);
    }

}
