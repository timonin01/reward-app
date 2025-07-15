package org.javaguru.reward.calculation.service.restclient.batch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardPaymentBatchRequest {

    private List<RewardPaymentRequest> rewards;

}
