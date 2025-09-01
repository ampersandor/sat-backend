package com.amperandor.sat_backend.controller;

import com.amperandor.sat_backend.dto.ArtifactDto;
import com.amperandor.sat_backend.service.ArtifactService;
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
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
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
    
}
