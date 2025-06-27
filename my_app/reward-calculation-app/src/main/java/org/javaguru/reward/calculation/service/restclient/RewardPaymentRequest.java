package org.javaguru.reward.calculation.service.restclient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RewardPaymentRequest {

    private Long employeeId;

    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal amount;

}
