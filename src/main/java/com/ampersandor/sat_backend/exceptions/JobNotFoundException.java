package com.ampersandor.sat_backend.exceptions;

public class JobNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Job [id=%s] is not found";

    public JobNotFoundException(String taskId) {
        super(MESSAGE.formatted("by taskId: " + taskId));
    }
}
