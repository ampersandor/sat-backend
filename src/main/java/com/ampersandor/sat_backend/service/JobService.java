package com.ampersandor.sat_backend.service;


import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.entity.Job;
import com.ampersandor.sat_backend.exceptions.ApplicationExceptions;
import com.ampersandor.sat_backend.mapper.JobMapper;
import com.ampersandor.sat_backend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final Sinks.Many<JobDto> jobSink;

    public Mono<JobDto> saveJob(Job job) {
        return jobRepository.save(job).map(JobMapper::toDto);
    }

    public Mono<JobDto> findJobByTaskId(String taskId) {
        return jobRepository.findByTaskId(taskId)
                .switchIfEmpty(ApplicationExceptions.jobNotFound(taskId))
                .map(JobMapper::toDto);
    }

    // TODO: should create ConcurrentHashMap to distinguish diverse sink
    public Flux<JobDto> getUpdates() {
        return jobSink.asFlux();
    }


    public Mono<Page<JobDto>> getJobs(Pageable pageable) {
        return Mono.zip(jobRepository.findBy(pageable).collectList(), jobRepository.count())
                .map(tuple -> {
                    List<JobDto> jobs = tuple.getT1().stream()
                            .map(JobMapper::toDto)
                            .collect(Collectors.toList());
                    Long total = tuple.getT2();
                    return new PageImpl<>(jobs, pageable, total);
                });
    }

}
