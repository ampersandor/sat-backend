package com.ampersandor.sat_backend.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(com.ampersandor.sat_backend.advice.Logging) || " +
            "@within(com.ampersandor.sat_backend.advice.Logging)")
    public Object measureMonoExecutionTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            if (result instanceof Mono<?> mono) {
                return mono
                        .doOnSuccess(data -> logSuccess(joinPoint, start))
                        .doOnError(e -> logError(joinPoint, start, e))
                        .doOnCancel(() -> logCancel(joinPoint, start));
            }

            if (result instanceof Flux<?> flux) {
                return flux
                        .doOnComplete(() -> logSuccess(joinPoint, start))
                        .doOnError(e -> logError(joinPoint, start, e))
                        .doOnCancel(() -> logCancel(joinPoint, start));
            }

            return result;
        } catch (Throwable e) {
            logError(joinPoint, start, e);
            throw new RuntimeException(e);
        }
    }

    private void logSuccess(ProceedingJoinPoint jp, long start) {
        long duration = System.currentTimeMillis() - start;
        log.info("Executed {} in {}ms", jp.getSignature().toShortString(), duration);
    }

    private void logError(ProceedingJoinPoint jp, long start, Throwable e) {
        long duration = System.currentTimeMillis() - start;
        log.error("Failed {} in {}ms", jp.getSignature().toShortString(), duration, e);
    }

    private void logCancel(ProceedingJoinPoint jp, long start) {
        long duration = System.currentTimeMillis() - start;
        log.error("Canceled {} in {}ms", jp.getSignature().toShortString(), duration);
    }
}
