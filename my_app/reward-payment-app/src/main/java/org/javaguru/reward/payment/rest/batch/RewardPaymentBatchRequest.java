package org.javaguru.reward.payment.rest.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.payment.rest.RewardPaymentRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardPaymentBatchRequest {

    private List<RewardPaymentRequest> rewards;

}
