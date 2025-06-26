package org.javaguru.reward.calculation.actions.paymentapp;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.paymentapp.payment.SearchPaymentResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class SearchPaymentAction {

    private static String baseUrl = "http://localhost:8090/api/test/payment/search";

    public PaymentDTO getPayment(Long employeeId, Double amount) {

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("employeeId", employeeId)
                .queryParam("amount", amount)
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        SearchPaymentResponse response = restTemplate.getForObject(uri, SearchPaymentResponse.class);

        return response.getPaymentDTO();
    }

}
