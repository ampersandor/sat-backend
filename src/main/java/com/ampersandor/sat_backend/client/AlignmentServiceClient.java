package com.ampersandor.sat_backend.client;


import com.ampersandor.sat_backend.domain.JobStatus;
import com.ampersandor.sat_backend.dto.AlignRequest;
import com.ampersandor.sat_backend.dto.AlignResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class AlignmentServiceClient {
    private final WebClient client;

    @CircuitBreaker(name = "align", fallbackMethod = "alignFallback")
    public Mono<AlignResponse> align(AlignRequest request) {
        return client.post()
                .uri("/align")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, r ->
                        r.bodyToMono(String.class).flatMap(body -> {
                            log.error("4xx error: status={}, body={}", r.statusCode(), body);
                            return Mono.error(new WebClientResponseException(
                                    r.statusCode().value(), "Client Error: " + body, null, null, null));
                        })
                )
                .onStatus(HttpStatusCode::is5xxServerError, r ->
                        r.bodyToMono(String.class).flatMap(body -> {
                            log.error("5xx error: status={}, body={}", r.statusCode(), body);
                            return Mono.error(new WebClientResponseException(
                                    r.statusCode().value(), "Server Error: " + body, null, null, null));
                        })
                )
                .bodyToMono(AlignResponse.class)
                .doOnSuccess(res -> log.info("Success: taskId={}", res.taskId()));
    }

    private Mono<AlignResponse> alignFallback(AlignRequest req, Throwable t) {
        log.warn("align Fallback 호출: tool={}, reason={}", req.tool(), t.toString());
        return Mono.just(new AlignResponse(JobStatus.ERROR, "DEGRADED"));
    }

}


