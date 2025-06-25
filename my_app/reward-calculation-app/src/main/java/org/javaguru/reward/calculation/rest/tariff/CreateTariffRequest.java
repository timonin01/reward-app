package org.javaguru.reward.calculation.rest.tariff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTariffRequest {

    private String jobType;

    private Double amount;

}
