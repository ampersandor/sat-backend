package com.amperandor.sat_backend.controller;

import com.amperandor.sat_backend.dto.ArtifactDto;
import com.amperandor.sat_backend.service.SATService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/sat")
public class SATController {
    private final SATService satService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<ArtifactDto>> uploadFile(@RequestPart("file") Mono<FilePart> filePartMono, ServerHttpRequest request) {
        return filePartMono.flatMap(filePart -> satService.saveInputFile(
                        filePart,
                        request.getHeaders().getContentLength()))
                .map(ResponseEntity::ok)
                .doOnSuccess(response -> log.info("successfully uploaded file"))
                .doOnError(error -> log.error("Something went wrong while uploading file", error))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

}
