package com.ampersandor.sat_backend.repository;

import com.ampersandor.sat_backend.entity.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JobRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Job> findByTaskId(String taskId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("task_id").is(taskId));
        return reactiveMongoTemplate.findOne(query, Job.class);
    }
    
    public Mono<Job> findById(String id) {
        return reactiveMongoTemplate.findById(id, Job.class);
    }

    public Flux<Job> findBy(Pageable pageable, Map<String, Object> filters) {
        Query query = buildQuery(filters);
        return reactiveMongoTemplate.find(query.with(pageable), Job.class);
    }

    public Mono<Long> count(Map<String, Object> filters) {
        Query query = buildQuery(filters);
        return reactiveMongoTemplate.count(query, Job.class);
    }

    public Mono<Job> save(Job job) {
        return reactiveMongoTemplate.save(job);
    }

    public Query buildQuery(Map<String, Object> filters) {
        Query query = new Query();
        if (filters.get("inputArtifactId") != null) {
            query.addCriteria(Criteria.where("input_artifact_id").is(filters.get("inputArtifactId")));
        }
        if (filters.get("tool") != null) {
            query.addCriteria(Criteria.where("align_tool").is(filters.get("tool").toString().toUpperCase()));
        }
        if (filters.get("status") != null) {
            query.addCriteria(Criteria.where("align_job_status").is(filters.get("status").toString().toUpperCase()));
        }

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        
        if (filters.get("startDate") != null) {
            LocalDate startDate = LocalDate.parse(filters.get("startDate").toString(), DateTimeFormatter.ISO_LOCAL_DATE);
            startDateTime = startDate.atStartOfDay();
        }
        if (filters.get("endDate") != null) {
            LocalDate endDate = LocalDate.parse(filters.get("endDate").toString(), DateTimeFormatter.ISO_LOCAL_DATE);
            endDateTime = endDate.atTime(23, 59, 59); // 23:59:59
        }
        
        if (startDateTime != null && endDateTime != null) {
            query.addCriteria(Criteria.where("created_at").gte(startDateTime).lte(endDateTime));
        } else if (startDateTime != null) {
            query.addCriteria(Criteria.where("created_at").gte(startDateTime));
        } else if (endDateTime != null) {
            query.addCriteria(Criteria.where("created_at").lte(endDateTime));
        }

        return query;
    }
}
