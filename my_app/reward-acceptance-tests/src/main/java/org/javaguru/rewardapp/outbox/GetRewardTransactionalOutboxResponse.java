package org.javaguru.rewardapp.outbox;

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
