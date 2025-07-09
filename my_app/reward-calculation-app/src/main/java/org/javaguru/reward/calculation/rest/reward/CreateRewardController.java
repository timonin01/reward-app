package org.javaguru.reward.calculation.rest.reward;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test/reward")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateRewardController {

    private final RewardRepository rewardRepository;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public CreateRewardResponse createReward(@RequestBody CreateRewardRequest request) {
        Reward reward = saveRewardInDb(request);
        RewardDTO dto = createRewardDto(reward);
        CreateRewardResponse response = new CreateRewardResponse(dto);
        log.info("Created CreateRewardResponse with status: {}", response.getRewardDTO().getRewardStatus());
        return response;
    }

    private Reward saveRewardInDb(CreateRewardRequest request){
        Reward reward = new Reward();
        reward.setEmployeeId(request.getEmployeeId());
        reward.setRewardStatus(request.getRewardStatus() != null
                ? RewardStatus.valueOf(request.getRewardStatus())
                : RewardStatus.NEW);
        reward.setJobType(JobType.valueOf(request.getJobType()));
        log.info("Saving reward with status: {}", reward.getRewardStatus());
        return rewardRepository.save(reward);
    }

    private RewardDTO createRewardDto(Reward reward){
        RewardDTO dto = new RewardDTO();
        dto.setId(reward.getId());
        dto.setEmployeeId(reward.getEmployeeId());
        dto.setRewardStatus(reward.getRewardStatus().name());
        dto.setJobType(reward.getJobType().name());
        log.info("Created RewardDTO with status: {}", dto.getRewardStatus());
        return dto;
    }

}
