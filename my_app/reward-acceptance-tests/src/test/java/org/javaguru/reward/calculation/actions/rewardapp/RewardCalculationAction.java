package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.rewardcalculation.RewardCalculationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RewardCalculationAction {

    private static String url = "http://localhost:8080/reward/calculation/";

    public void calculateRewards() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, null, RewardCalculationResponse.class);
    }

}
