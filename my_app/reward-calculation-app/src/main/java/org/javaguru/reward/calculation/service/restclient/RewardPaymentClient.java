package org.javaguru.reward.calculation.service.restclient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentClient {

    private final RestClient rewardPaymentRestClient;

    public void payReward(Long employeeId, Double amount) {
        RewardPaymentRequest request = new RewardPaymentRequest(employeeId, amount);

        rewardPaymentRestClient.post()
                .uri("/reward/payment/")
                .body(request)
                .retrieve()
                .body(RewardPaymentResponse.class);
    }

}
