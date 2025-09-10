package com.ampersandor.sat_backend.repository;

import com.ampersandor.sat_backend.entity.Job;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface JobRepository extends ReactiveMongoRepository<Job, String> {
    Mono<Job> findByTaskId(String taskId);

    Flux<Job> findBy(Pageable pageable);

}
