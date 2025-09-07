package com.ampersandor.sat_backend.service;


import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.entity.Job;
import com.ampersandor.sat_backend.mapper.JobMapper;
import com.ampersandor.sat_backend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Mono<JobDto> saveJob(Job job) {
        return jobRepository.save(job).map(JobMapper::toDto);
    }


}
