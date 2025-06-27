package org.javaguru.reward.calculation.service.restclient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.calculation.deserialization.BigDecimalJsonDeserializer;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RewardPaymentRequest {

    private Long employeeId;

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    private BigDecimal amount;

}
