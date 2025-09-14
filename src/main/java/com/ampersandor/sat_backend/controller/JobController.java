package com.ampersandor.sat_backend.controller;

import com.ampersandor.sat_backend.advice.Logging;
import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@RequestMapping("api/v1/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<JobDto>> subscribe() {
        return jobService.getUpdates()
                .map(data -> ServerSentEvent.<JobDto>builder()
                        .data(data)
                        .build());
    }

    // TODO: accept sort options and parse it
    @GetMapping("")
    public Mono<Page<JobDto>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return jobService.getJobs(pageable);
    }

    @GetMapping("/statistic/{jobId}")
    public Mono<ResponseEntity<StatisticDto>> getStatistic(@PathVariable String jobId) {
        return jobService.getStatistic(jobId)
                .map(ResponseEntity::ok)
                .doOnError(error -> log.error("statistic error", error))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

}
