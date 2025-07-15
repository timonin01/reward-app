package org.javaguru.reward.calculation.service.restclient.batch;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.config.RetryConfig;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentBatchClient {

    //    Создается в RewardPaymentClientConfig
    private final RestClient rewardPaymentRestClient;

    @Retry(name = RetryConfig.PAY_REWARD_CONFIG, fallbackMethod = "fallback")
    public Map<Reward, String> payRewards(List<Reward> rewards) {
        RewardPaymentBatchRequest request = new RewardPaymentBatchRequest();
        request.setRewards(rewards.stream().
                map(reward -> new RewardPaymentRequest(reward.getEmployeeId(),reward.getId(),reward.getAmount())).
                toList());

        RewardPaymentBatchResponse response = rewardPaymentRestClient.post()
                .uri("/reward/payment/batch/")
                .body(request)
                .retrieve()
                .body(RewardPaymentBatchResponse.class);
        return mapRewardStatusesMap(rewards, response);
    }

    private Map<Reward, String> mapRewardStatusesMap(List<Reward> rewards, RewardPaymentBatchResponse response) {
        Map<Reward, String> rewardStatuses = new HashMap<>();
        if (response != null && response.getPaymentStatuses() != null) {
            for (RewardPaymentStatusDTO paymentStatus : response.getPaymentStatuses()) {
                String status = paymentStatus.getPaymentStatus();
                Reward reward = rewards.stream()
                        .filter(r ->
                                paymentStatus.getPaymentRequest().getRewardId().equals(r.getId())
                                        && paymentStatus.getPaymentRequest().getEmployeeId().equals(r.getEmployeeId())
                        )
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown payment!!! payment.employeeId = "
                                + paymentStatus.getPaymentRequest().getEmployeeId()
                                + " payment.rewardId = " + paymentStatus.getPaymentRequest().getRewardId()));
                rewardStatuses.put(reward, status);
            }
        }
        return rewardStatuses;
    }

    public Map<Reward, String> fallback(List<Reward> rewards, Exception ex) throws Exception {
        log.error("Error: payRewards Batch retry exception", ex);
        throw ex;
    }

}
