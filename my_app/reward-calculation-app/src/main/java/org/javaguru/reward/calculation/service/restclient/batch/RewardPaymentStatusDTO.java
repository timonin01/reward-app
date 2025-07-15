package org.javaguru.reward.calculation.service.restclient.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardPaymentStatusDTO {

    private RewardPaymentRequest paymentRequest;

    private String paymentStatus;

}
