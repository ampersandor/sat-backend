package com.ampersandor.sat_backend.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {
    public static <T> Mono<T> artifactsNotFound(String artifactId) {
        return Mono.error(new ArtifactNotFoundException(artifactId));
    }
    public static <T> Mono<T> jobNotFound(String taskId) {
        return Mono.error(new JobNotFoundException(taskId));
    }
    public static <T> Mono<T> jobNotFoundByTaskId(String taskId) {
        return Mono.error(new JobNotFoundByTaskIdException(taskId));
    }
}
