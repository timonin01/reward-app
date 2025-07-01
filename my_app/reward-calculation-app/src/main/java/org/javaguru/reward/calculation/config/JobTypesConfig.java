package org.javaguru.reward.calculation.config;

import org.javaguru.reward.calculation.service.domain.JobType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "job")
public class JobTypesConfig {

    private List<JobType> jobTypes;

    public List<JobType> getJobTypes() {
        return jobTypes;
    }

    public void setJobTypes(String jobTypes) {
        this.jobTypes =  Arrays.stream(jobTypes.split(","))
                .map(String::trim) // Remove extra spaces
                .map(JobType::valueOf) // Convert string to enum
                .collect(Collectors.toList());
    }

}
