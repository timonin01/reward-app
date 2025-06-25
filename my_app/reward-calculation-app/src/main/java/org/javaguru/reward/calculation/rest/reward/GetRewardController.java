package org.javaguru.reward.calculation.rest.reward;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.rest.employee.EmployeeDTO;
import org.javaguru.reward.calculation.rest.employee.GetEmployeeResponse;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/test/reward")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetRewardController {

    private final RewardRepository rewardRepository;

    @GetMapping(path = "/{id}",
            produces = "application/json")
    public GetRewardResponse createReward(@PathVariable Long id) {
        RewardDTO dto = searchEmployeeInDb(id);
        return new GetRewardResponse(dto);
    }

    private RewardDTO searchEmployeeInDb(Long id){
        Optional<Reward> reward = rewardRepository.findById(id);
        if(reward.isPresent()){
            RewardDTO dto = createRewardDTO(reward.get());
            return dto;
        }
        throw new IllegalArgumentException("Employee not found by id " + id);
    }

    private RewardDTO createRewardDTO(Reward reward){
        RewardDTO rewardDTO = new RewardDTO();
        rewardDTO.setId(reward.getId());
        rewardDTO.setEmployeeId(reward.getEmployeeId());
        rewardDTO.setJobType(reward.getJobType());
        rewardDTO.setStatus(reward.getStatus());
        return rewardDTO;
    }

}
