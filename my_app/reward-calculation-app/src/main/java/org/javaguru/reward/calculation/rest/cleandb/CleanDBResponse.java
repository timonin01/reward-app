package org.javaguru.reward.calculation.rest.cleandb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CleanDBResponse {

    private boolean employeeDeleted;
    private boolean rewardDeleted;
    private boolean tariffDeleted;
    private boolean jobTypesDeleted;

}
