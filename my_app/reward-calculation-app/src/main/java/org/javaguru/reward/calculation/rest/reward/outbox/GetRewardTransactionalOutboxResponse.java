package org.javaguru.reward.calculation.rest.reward.outbox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetRewardTransactionalOutboxResponse {

    private RewardTransactionalOutboxDTO  rewardTransactionalOutboxDTO;

}
