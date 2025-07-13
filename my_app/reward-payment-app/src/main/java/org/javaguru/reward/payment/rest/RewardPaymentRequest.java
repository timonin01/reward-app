package org.javaguru.reward.payment.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.payment.serialization.BigDecimalJsonDeserializer;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RewardPaymentRequest {

    private Long employeeId;

    private Long rewardId;

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    private BigDecimal amount;

}
