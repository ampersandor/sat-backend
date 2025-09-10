package com.ampersandor.sat_backend.controller;


import com.ampersandor.sat_backend.advice.Logging;
import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.dto.JobRequest;
import com.ampersandor.sat_backend.dto.JobUpdateRequest;
import com.ampersandor.sat_backend.service.AnalyzeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@Logging
@RequestMapping("api/v1/analyze")
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    @PostMapping("/align/{artifactId}")
    public Mono<ResponseEntity<JobDto>> alignFile(@PathVariable String artifactId, @RequestBody Mono<JobRequest> jobRequestMono) {
        return jobRequestMono.flatMap(jobRequest -> analyzeService.align(artifactId, jobRequest))
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());

    }

    @PostMapping("/update")
    public Mono<ResponseEntity<JobDto>> updateAlignJob(@RequestBody JobUpdateRequest updateRequest) {
        return analyzeService.updateJob(updateRequest)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<JobDto>> subscribe() {
        return analyzeService.streamJobs()
                .map(data -> ServerSentEvent.<JobDto>builder()
                        .data(data)
                        .build());
    }
}
