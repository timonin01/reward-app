package org.javaguru.reward.calculation.service.restclient;

import io.github.resilience4j.retry.annotation.Retry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.config.RetryConfig;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentClient {

//    Создается в RewardPaymentClientConfig
    private final RestClient rewardPaymentRestClient;

    @Retry(name = RetryConfig.PAY_REWARD_CONFIG, fallbackMethod = "fallback")
    public RewardPaymentResponse payReward(Long employeeId, Long rewardId,BigDecimal amount) {
        RewardPaymentRequest request = new RewardPaymentRequest(employeeId, rewardId, amount);

        return rewardPaymentRestClient.post()
                .uri("/reward/payment/")
                .header("traceparent", buildTraceParent())
                .body(request)
                .retrieve()
                .body(RewardPaymentResponse.class);
    }

    public RewardPaymentResponse fallback(Long employeeId,
                                          Long rewardId,
                                          BigDecimal amount,
                                          Exception ex) throws Exception {
        log.error("Error: payReward retry exception"
                        + " employeeId = " + employeeId
                        + " rewardId = " + rewardId
                        + " amount = " + amount
                , ex);
        throw ex;
    }

    private String buildTraceParent() {
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();

        if (spanContext.isValid()) {
            String traceId = spanContext.getTraceId();
            String spanId = spanContext.getSpanId();
            String traceFlags = spanContext.getTraceFlags().asHex(); // "01" for sampled, "00" for not sampled

            // Construct traceparent header manually
            return "00-" + traceId + "-" + spanId + "-" + traceFlags;
        } else {
            return "";
        }
    }

}
