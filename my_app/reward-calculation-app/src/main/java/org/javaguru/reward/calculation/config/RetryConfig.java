package org.javaguru.reward.calculation.config;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.retry.event.RetryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RetryConfig {

    public static final String PAY_REWARD_CONFIG = "payRewardRetry";

    @Bean
    public Retry payRewardRetryInstance(RetryRegistry retryRegistry) {
        Retry retry = retryRegistry.retry(PAY_REWARD_CONFIG);

        // Add a listener for this specific Retry instance
        retry.getEventPublisher().onRetry(event -> logRetry(event));

        return retry;
    }

    private void logRetry(RetryEvent event) {
        log.warn("Retry attempt {} for '{}' failed. Exception: {}",
                event.getNumberOfRetryAttempts(),
                event.getName(),
                event.getLastThrowable() != null ? event.getLastThrowable().getMessage() : "unknown");
    }

}
