package com.ampersandor.sat_backend.advice;

import com.ampersandor.sat_backend.exceptions.ArtifactNotFoundException;
import com.ampersandor.sat_backend.exceptions.JobNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ArtifactNotFoundException.class)
    public ProblemDetail handleException(ArtifactNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex, problem -> {
            problem.setType(URI.create("https://localhost:3000"));
            problem.setTitle("Artifacts Not Found");
        });
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ProblemDetail handleException(JobNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex, problem -> {
            problem.setType(URI.create("https://localhost:3000"));
            problem.setTitle("Job Not Found");
        });
    }

    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problemDetail);
        return problemDetail;
    }
}
