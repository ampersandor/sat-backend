package com.ampersandor.sat_backend.service;

import com.ampersandor.sat_backend.client.AlignmentServiceClient;
import com.ampersandor.sat_backend.dto.AlignRequest;
import com.ampersandor.sat_backend.dto.AlignResponse;
import com.ampersandor.sat_backend.dto.JobDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlignmentService {
    private final AlignmentServiceClient alignmentServiceClient;
    private final Sinks.Many<JobDto> jobSink;

    public Mono<JobDto> submit(JobDto jobDto) {
        jobSink.tryEmitNext(jobDto);
        return alignmentServiceClient
                .align(new AlignRequest(jobDto.getTool(), jobDto.getOptions(), jobDto.getBaseName(), jobDto.getDirName()))
                .map(response -> this.update(response, jobDto));
    }

    private JobDto update(AlignResponse response, JobDto jobDto) {
        jobDto.setTaskId(response.taskId());
        jobDto.setJobStatus(response.jobStatus());
        jobSink.tryEmitNext(jobDto);
        return jobDto;
    }
}
