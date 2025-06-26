package org.javaguru.rewardapp.tariff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TariffDTO {

    private Long id;

    private String jobType;

    private Double amount;

}
