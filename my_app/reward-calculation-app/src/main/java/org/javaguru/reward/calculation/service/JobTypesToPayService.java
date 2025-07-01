package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.config.JobTypesConfig;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.repositories.JobTypesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class JobTypesToPayService {

    private static final List<JobType> JOB_TYPES_TO_PAY =
            List.of(JobType.SPEECH, JobType.LESSON, JobType.HELP);

    private final JobTypesRepository jobTypesRepository;
    private final JobTypesConfig jobTypesConfig;

    @Value( "${job.types.to.pay.loading}" )
    private String jobTypesToPayLoading;

    public List<JobType> loadJobTypesToPay(){
        if(jobTypesToPayLoading.equals("CONSTANT")){
            return JOB_TYPES_TO_PAY;
        }
        else if(jobTypesToPayLoading.equals("PROPERTIES")){
            return jobTypesConfig.getJobTypes();
        }
        else if (jobTypesToPayLoading.equals("DATABASE")){
            return jobTypesRepository.findAllDistinctJobTypes();
        }
        throw new IllegalStateException("Unsupported load type!");
    }

}
