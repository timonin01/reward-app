package org.javaguru.reward.calculation.rest.jobtypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaguru.reward.calculation.service.domain.JobType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobTypesDTO {

    private Long id;

    private String jobType;

}
