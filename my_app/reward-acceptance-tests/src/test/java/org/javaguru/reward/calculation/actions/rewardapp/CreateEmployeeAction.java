package org.javaguru.reward.calculation.actions.rewardapp;

import org.javaguru.rewardapp.employee.CreateEmployeeRequest;
import org.javaguru.rewardapp.employee.CreateEmployeeResponse;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class CreateEmployeeAction {

    private static String url = "http://localhost:8080/api/test/employee/";

    public EmployeeDTO createEmployee(String firstName,
                                      String lastName,
                                      BigDecimal bonusCoefficient) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        CreateEmployeeRequest request = new CreateEmployeeRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setBonusCoefficient(bonusCoefficient);

        HttpEntity<CreateEmployeeRequest> requestEntity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        CreateEmployeeResponse response = restTemplate.postForObject(url, requestEntity, CreateEmployeeResponse.class);

        return response.getEmployeeDTO();
    }

}
