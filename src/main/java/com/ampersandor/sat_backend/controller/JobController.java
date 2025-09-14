package com.ampersandor.sat_backend.controller;

import com.ampersandor.sat_backend.advice.Logging;
import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.dto.StatisticDto;
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

import java.util.HashMap;
import java.util.Map;


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

    @GetMapping("")
    public Mono<Page<JobDto>> getJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String inputArtifactId,
            @RequestParam(required = false) String tool,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Map<String, Object> filters = new HashMap<>();
        if (inputArtifactId != null && !inputArtifactId.trim().isEmpty())
            filters.put("inputArtifactId", inputArtifactId);
        if (tool != null && !tool.trim().isEmpty())
            filters.put("tool", tool);
        if (status != null && !status.trim().isEmpty())
            filters.put("status", status);
        if (startDate != null && !startDate.trim().isEmpty())
            filters.put("startDate", startDate);
        if (endDate != null && !endDate.trim().isEmpty())
            filters.put("endDate", endDate);

        return jobService.getJobs(pageable, filters);
    }

    @GetMapping("/statistic/{jobId}")
    public Mono<ResponseEntity<StatisticDto>> getStatistic(@PathVariable String jobId) {
        return jobService.getStatistic(jobId)
                .map(ResponseEntity::ok)
                .doOnError(error -> log.error("statistic error", error))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }
}
