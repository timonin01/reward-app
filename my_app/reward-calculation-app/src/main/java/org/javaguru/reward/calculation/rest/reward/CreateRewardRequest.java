package org.javaguru.reward.calculation.rest.reward;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRewardRequest {

    private Long employeeId;

    private String jobType;

    private String rewardStatus;

}
