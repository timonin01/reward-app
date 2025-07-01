package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.jobtypes.CreateJobTypeRequest;
import org.javaguru.rewardapp.jobtypes.CreateJobTypeResponse;
import org.javaguru.rewardapp.jobtypes.JobTypesDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class CreateJobTypesAction {

    private static String url = "http://localhost:8080/api/test/jobTypes/";

    public JobTypesDTO createAllowedToPayJobType(String jobType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateJobTypeRequest request = new CreateJobTypeRequest();
        request.setJobType(jobType);

        HttpEntity<CreateJobTypeRequest> requestEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        CreateJobTypeResponse response = restTemplate.postForObject(url, requestEntity, CreateJobTypeResponse.class);

        return response.getJobTypesDTO();
    }

}
