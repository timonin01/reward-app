package org.javaguru.reward.calculation.rest.employee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.calculation.deserialization.BigDecimalJsonDeserializer;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {

    private String firstName;

    private String lastName;

    @JsonDeserialize(using = BigDecimalJsonDeserializer.class)
    private BigDecimal bonusCoefficient;

}
