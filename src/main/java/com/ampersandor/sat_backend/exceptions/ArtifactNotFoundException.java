package com.ampersandor.sat_backend.exceptions;

public class ArtifactNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Artifact [id=%s] is not found";

    public ArtifactNotFoundException(String artifactId) {
        super(MESSAGE.formatted(artifactId));
    }
}
