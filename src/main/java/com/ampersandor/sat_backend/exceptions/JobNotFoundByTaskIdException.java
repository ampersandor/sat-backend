package com.ampersandor.sat_backend.exceptions;

public class JobNotFoundByTaskIdException extends RuntimeException {
    private static final String MESSAGE = "Job is not found by task id=%s";

    public JobNotFoundByTaskIdException(String taskId) {
        super(MESSAGE.formatted(taskId));
    }
}
