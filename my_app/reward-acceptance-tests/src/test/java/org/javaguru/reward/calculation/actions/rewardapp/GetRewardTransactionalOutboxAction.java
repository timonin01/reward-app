package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.outbox.GetRewardTransactionalOutboxResponse;
import org.javaguru.rewardapp.outbox.RewardTransactionalOutboxDTO;
import org.springframework.web.client.RestTemplate;

public class GetRewardTransactionalOutboxAction {

    private static String baseUrl = "http://localhost:8080/api/test/reward/transactional-outbox/getByRewardId/";

    public RewardTransactionalOutboxDTO getRewardTransactionalOutbox(Long rewardId) {
        String uri = baseUrl + rewardId;

        RestTemplate restTemplate = new RestTemplate();
        GetRewardTransactionalOutboxResponse response = restTemplate.getForObject(uri, GetRewardTransactionalOutboxResponse.class);

        return response.getRewardTransactionalOutboxDTO();
    }

}
