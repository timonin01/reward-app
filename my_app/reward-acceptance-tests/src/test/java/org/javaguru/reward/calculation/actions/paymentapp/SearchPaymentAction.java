package org.javaguru.reward.calculation.actions.paymentapp;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.paymentapp.payment.SearchPaymentResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class SearchPaymentAction {

    private static String baseUrl = "http://localhost:8090/api/test/payment/search";

    public List<PaymentDTO> getPayments(Long employeeId, Double amount) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("employeeId", employeeId);
        if (amount != null) {
            builder = builder.queryParam("amount", amount);
        }
        URI uri = builder.build().toUri();

        RestTemplate restTemplate = new RestTemplate();
        SearchPaymentResponse response = restTemplate.getForObject(uri, SearchPaymentResponse.class);

        return response.getPaymentsDTO();
    }

}
