package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.cleandb.CleanRewardDbRequest;
import org.javaguru.rewardapp.cleandb.CleanRewardDbResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class CleanRewardDatabaseAction {

    private static String url = "http://localhost:8080/api/test/cleandb/";

    public CleanRewardDbResponse cleanRewardDb(boolean cleanEmployee,
                                               boolean cleanReward,
                                               boolean cleanTariff) {
        CleanRewardDbRequest request = new CleanRewardDbRequest();
        request.setCleanEmployee(cleanEmployee);
        request.setCleanReward(cleanReward);
        request.setCleanTariff(cleanTariff);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CleanRewardDbRequest> requestEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, requestEntity, CleanRewardDbResponse.class);
    }

}
