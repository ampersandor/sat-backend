package com.ampersandor.sat_backend.service;


import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.dto.StatisticDto;
import com.ampersandor.sat_backend.entity.Job;
import com.ampersandor.sat_backend.exceptions.ApplicationExceptions;
import com.ampersandor.sat_backend.mapper.JobMapper;
import com.ampersandor.sat_backend.mapper.StatisticMapper;
import com.ampersandor.sat_backend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final JobStream jobStream;

    public Mono<JobDto> saveJob(Job job) {
        return jobRepository.save(job)
                .map(JobMapper::toDto)
                .map(jobStream::publish);
    }

    public Mono<JobDto> findJobByTaskId(String taskId) {
        return jobRepository.findByTaskId(taskId)
                .switchIfEmpty(ApplicationExceptions.jobNotFoundByTaskId(taskId))
                .map(JobMapper::toDto);
    }

    public Flux<JobDto> getUpdates() {
        return jobStream.stream();
    }

    public Mono<Page<JobDto>> getJobs(Pageable pageable, Map<String, Object> filters) {
        return Mono.zip(jobRepository.findBy(pageable, filters).collectList(), jobRepository.count(filters))
                .map(tuple -> {
                    List<JobDto> jobs = tuple.getT1().stream()
                            .map(JobMapper::toDto)
                            .collect(Collectors.toList());
                    Long total = tuple.getT2();
                    return new PageImpl<>(jobs, pageable, total);
                });
    }

    public Mono<StatisticDto> getStatistic(String jobId) {
        return jobRepository.findById(jobId)
                .switchIfEmpty(ApplicationExceptions.jobNotFound(jobId))
                .map(job -> StatisticMapper.toDto(job.getStatistic()));
    }

}
