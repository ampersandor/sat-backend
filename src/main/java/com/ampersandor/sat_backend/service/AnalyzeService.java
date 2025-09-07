package com.ampersandor.sat_backend.service;

import com.ampersandor.sat_backend.advice.Logging;
import com.ampersandor.sat_backend.dto.JobDto;
import com.ampersandor.sat_backend.dto.JobRequest;
import com.ampersandor.sat_backend.mapper.JobMapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Logging
@RequiredArgsConstructor
@Service
@Transactional
public class AnalyzeService {

    private final JobService jobService;
    private final ArtifactService artifactService;
    private final AlignmentService alignmentService;

    public Mono<JobDto> align(String artifactId, JobRequest jobRequest) {
        return artifactService.getArtifact(artifactId)
                .map(artifactDto -> JobMapper.toEntity(jobRequest, artifactDto))
                .flatMap(jobService::saveJob)
                .flatMap(alignmentService::submit)
                .map(JobMapper::toEntity)
                .flatMap(jobService::saveJob);
    }

}
