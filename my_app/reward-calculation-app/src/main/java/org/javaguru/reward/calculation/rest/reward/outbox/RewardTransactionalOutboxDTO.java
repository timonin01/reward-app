package org.javaguru.reward.calculation.rest.reward.outbox;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardTransactionalOutboxDTO {

    private Long id;

    private Long rewardId;

    private String status;

}
