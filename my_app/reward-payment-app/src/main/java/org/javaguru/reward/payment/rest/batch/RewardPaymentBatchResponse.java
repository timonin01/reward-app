package org.javaguru.reward.payment.rest.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardPaymentBatchResponse {

    private List<RewardPaymentStatusDTO> paymentStatuses;

}
