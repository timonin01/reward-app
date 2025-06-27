package org.javaguru.reward.calculation.rest.reward;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new CreateRewardResponse(dto);
    }

    private Reward saveRewardInDb(CreateRewardRequest request){
        Reward reward = new Reward();
        reward.setEmployeeId(request.getEmployeeId());
        reward.setStatus(request.getStatus() != null ? RewardStatus.valueOf(request.getStatus()) : null);
        reward.setJobType(JobType.valueOf(request.getJobType()));
        rewardRepository.save(reward);
        return reward;
    }

    private RewardDTO createRewardDto(Reward reward){
        RewardDTO dto = new RewardDTO();
        dto.setId(reward.getId());
        dto.setEmployeeId(reward.getEmployeeId());
        dto.setStatus(reward.getStatus() != null ? reward.getStatus().name() : null);
        dto.setJobType(reward.getJobType().name());
        return dto;
    }

}
