package com.ampersandor.sat_backend.repository;

import com.ampersandor.sat_backend.entity.Job;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface JobRepository extends ReactiveMongoRepository<Job, String> {
}
