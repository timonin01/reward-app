package org.javaguru.reward.calculation.rest.jobtypes;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.JobTypes;
import org.javaguru.reward.calculation.service.repositories.JobTypesRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/jobTypes")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateJobTypeController {

    private final JobTypesRepository jobTypesRepository;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public CreateJobTypeResponse createJobType(@RequestBody CreateJobTypeRequest createJobTypeRequest){
        JobTypes jobTypes = storeJobTypeInDB(createJobTypeRequest);
        JobTypesDTO jobTypesDTO = convertToDTO(jobTypes);
        return new CreateJobTypeResponse(jobTypesDTO);
    }

    private JobTypes storeJobTypeInDB(CreateJobTypeRequest createJobTypeRequest){
        JobTypes jobTypes = new JobTypes();
        jobTypes.setJobType(JobType.valueOf(createJobTypeRequest.getJobType()));
        jobTypesRepository.save(jobTypes);
        return jobTypes;
    }

    private JobTypesDTO convertToDTO(JobTypes jobTypes){
        JobTypesDTO jobTypesDTO = new JobTypesDTO();
        jobTypesDTO.setId(jobTypes.getId());
        jobTypesDTO.setJobType(jobTypes.getJobType().name());
        return jobTypesDTO;
    }

}
