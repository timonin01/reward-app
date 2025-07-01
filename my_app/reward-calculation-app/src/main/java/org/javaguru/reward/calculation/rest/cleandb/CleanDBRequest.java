package org.javaguru.reward.calculation.rest.cleandb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CleanDBRequest {

    private boolean cleanEmployee;
    private boolean cleanReward;
    private boolean cleanTariff;
    private boolean cleanJobTypes;

}
