package org.javaguru.reward.calculation.service.restclient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentClient {

//    Создается в RewardPaymentClientConfig
    private final RestClient rewardPaymentRestClient;

    public void payReward(Long employeeId, BigDecimal amount) {
        RewardPaymentRequest request = new RewardPaymentRequest(employeeId, amount);

        rewardPaymentRestClient.post()
                .uri("/reward/payment/")
                .body(request)
                .retrieve()
                .body(RewardPaymentResponse.class);
    }

}
