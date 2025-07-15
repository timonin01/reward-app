package org.javaguru.reward.payment.rest.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.payment.rest.RewardPaymentRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardPaymentStatusDTO {

    private RewardPaymentRequest paymentRequest;

    private String paymentStatus;

}
