package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.tariff.CreateTariffRequest;
import org.javaguru.rewardapp.tariff.CreateTariffResponse;
import org.javaguru.rewardapp.tariff.TariffDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class CreateTariffAction {

    private static String url = "http://localhost:8080/api/test/tariff/";

    public TariffDTO createTariff(String jobType,
                                  Double amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateTariffRequest request = new CreateTariffRequest();
        request.setJobType(jobType);
        request.setAmount(amount);

        HttpEntity<CreateTariffRequest> requestEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        CreateTariffResponse response = restTemplate.postForObject(url, requestEntity, CreateTariffResponse.class);

        return response.getTariffDTO();
    }

}
