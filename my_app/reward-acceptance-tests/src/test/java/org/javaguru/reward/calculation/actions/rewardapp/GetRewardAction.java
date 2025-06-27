package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.reward.GetRewardResponse;
import org.javaguru.rewardapp.reward.RewardDTO;
import org.springframework.web.client.RestTemplate;

public class GetRewardAction {

    private static String baseUrl = "http://localhost:8080/api/test/reward/";

    public RewardDTO getReward(Long rewardId) {
        String uri = baseUrl + rewardId;

        RestTemplate restTemplate = new RestTemplate();
        GetRewardResponse response = restTemplate.getForObject(uri, GetRewardResponse.class);

        return response.getRewardDTO();
    }

}
