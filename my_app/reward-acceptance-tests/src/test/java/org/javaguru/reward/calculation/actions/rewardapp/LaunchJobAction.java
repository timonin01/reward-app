package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.job.LaunchJobResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class LaunchJobAction {

    private static String url = "http://localhost:8080/api/test/reward/transactional-outbox/job/";

    public void launchJob() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, null, LaunchJobResponse.class);
    }

}
