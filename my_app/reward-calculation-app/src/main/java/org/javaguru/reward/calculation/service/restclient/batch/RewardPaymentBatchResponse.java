package org.javaguru.reward.calculation.service.restclient.batch;

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
