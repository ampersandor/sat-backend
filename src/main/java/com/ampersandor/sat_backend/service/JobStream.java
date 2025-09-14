package com.ampersandor.sat_backend.service;

import com.ampersandor.sat_backend.dto.JobDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
public class JobStream {

    // TODO: should create ConcurrentHashMap to distinguish diverse sink
    private final Sinks.Many<JobDto> sink;

    public JobDto publish(JobDto dto) {
        sink.tryEmitNext(dto);
        return dto;
    }

    public Flux<JobDto> stream() {
        return sink.asFlux();
    }

}
