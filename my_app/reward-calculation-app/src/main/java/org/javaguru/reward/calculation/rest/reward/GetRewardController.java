package org.javaguru.reward.calculation.rest.reward;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.rest.employee.EmployeeDTO;
import org.javaguru.reward.calculation.rest.employee.GetEmployeeResponse;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/test/reward")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetRewardController {

    private final RewardRepository rewardRepository;

    @GetMapping(path = "/{id}",
            produces = "application/json")
    public GetRewardResponse createReward(@PathVariable Long id) {
        RewardDTO dto = searchEmployeeInDb(id);
        log.info("Send successful response");
        return new GetRewardResponse(dto);
    }

    private RewardDTO searchEmployeeInDb(Long id){
        Optional<Reward> reward = rewardRepository.findById(id);
        if(reward.isPresent()){
            log.info("Creating DTO");
            return createRewardDTO(reward.get());
        }
        throw new IllegalArgumentException("Reward not found by id " + id);
    }

    private RewardDTO createRewardDTO(Reward reward){
        RewardDTO rewardDTO = new RewardDTO();
        rewardDTO.setId(reward.getId());
        rewardDTO.setEmployeeId(reward.getEmployeeId());
        rewardDTO.setJobType(reward.getJobType().name());
        rewardDTO.setRewardStatus(reward.getRewardStatus().name());
        return rewardDTO;
    }

}
