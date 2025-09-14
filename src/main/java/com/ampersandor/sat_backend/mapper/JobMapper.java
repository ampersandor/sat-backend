package com.ampersandor.sat_backend.mapper;

import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.dto.*;
import com.ampersandor.sat_backend.entity.Job;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class JobMapper {


    public static JobDto toDto(Job job) {
        JobDto jobDto = new JobDto();
        BeanUtils.copyProperties(job, jobDto);

        return jobDto;
    }

    public static Job toEntity(JobDto jobDto) {
        Job job = new Job();
        BeanUtils.copyProperties(jobDto, job);

        return job;
    }

    public static Job toEntity(JobRequest jobRequest, ArtifactDto artifactDto) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));;

        return Job.builder()
                .inputArtifactId(artifactDto.id())
                .baseName(artifactDto.filename())
                .dirName(artifactDto.directory())
                .tool(jobRequest.tool())
                .options(jobRequest.options())
                .createdAt(now)
                .updatedAt(now)
                .jobStatus(JobStatus.PENDING)
                .build();
    }

    public static Job toEntity(JobDto jobDto, JobUpdateRequest jobUpdateRequest) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Job job = toEntity(jobDto);
        job.setJobStatus(jobUpdateRequest.status());
        job.setMessage(jobUpdateRequest.message());
        job.setUpdatedAt(now);
        return job;
    }

    public static Job toEntity(JobDto jobDto, JobUpdateRequest jobUpdateRequest, ArtifactDto alignArtifactDto, ArtifactDto statArtifactDto) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Job job = toEntity(jobDto);
        job.setAlignArtifactId(alignArtifactDto.id());
        job.setStatArtifactId(statArtifactDto.id());
        job.setStatistic(jobUpdateRequest.statistic());
        job.setJobStatus(jobUpdateRequest.status());
        job.setMessage(jobUpdateRequest.message());
        job.setUpdatedAt(now);
        return job;
    }




}
