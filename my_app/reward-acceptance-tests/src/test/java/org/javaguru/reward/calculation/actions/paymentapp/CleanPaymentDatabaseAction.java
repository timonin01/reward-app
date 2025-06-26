package org.javaguru.reward.calculation.actions.paymentapp;

import org.javaguru.paymentapp.cleandb.CleanPaymentDbRequest;
import org.javaguru.paymentapp.cleandb.CleanPaymentDbResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class CleanPaymentDatabaseAction {

    private static String url = "http://localhost:8090/api/test/cleandb/";

    public CleanPaymentDbResponse cleanDb(boolean cleanPayment) {
        CleanPaymentDbRequest request = new CleanPaymentDbRequest(cleanPayment);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CleanPaymentDbRequest> requestEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, requestEntity, CleanPaymentDbResponse.class);
    }

}
