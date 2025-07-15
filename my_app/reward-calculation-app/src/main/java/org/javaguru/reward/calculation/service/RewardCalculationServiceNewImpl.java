package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.*;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@ConditionalOnProperty(name = {"transactionalOutbox"},havingValue = "true")
public class RewardCalculationServiceNewImpl implements RewardCalculationService{

    private final JobTypesToPayService jobTypesToPayService;
    private final RewardRepository rewardRepository;
    private final RewardCalculationProcessRewardService rewardCalculationProcessRewardService;

    @Override
    public void calculateRewards(List<Employee> employees) {
        employees.forEach(employee ->  findAllRewardsByEmployeeIdAndRewardStatusAndJobType(employee)
                .forEach(reward -> rewardCalculationProcessRewardService.processReward(employee,reward.getId()))
        );
    }

    private List<Reward> findAllRewardsByEmployeeIdAndRewardStatusAndJobType(Employee employee){
        return rewardRepository.findByEmployeeIdAndRewardStatusAndJobTypeIn(
                employee.getId(), RewardStatus.NEW, jobTypesToPayService.loadJobTypesToPay());
    }

}
