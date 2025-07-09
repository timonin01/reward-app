package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.reward.CreateRewardRequest;
import org.javaguru.rewardapp.reward.CreateRewardResponse;
import org.javaguru.rewardapp.reward.RewardDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class CreateRewardAction {

    private static String url = "http://localhost:8080/api/test/reward/";

    public RewardDTO createReward(Long employeeId,
                                  String jobType,
                                  String rewardStatus) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateRewardRequest request = new CreateRewardRequest();
        request.setEmployeeId(employeeId);
        request.setJobType(jobType);
        request.setRewardStatus(rewardStatus);

        HttpEntity<CreateRewardRequest> requestEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        CreateRewardResponse response = restTemplate.postForObject(url, requestEntity, CreateRewardResponse.class);

        return response.getRewardDTO();
    }

}
