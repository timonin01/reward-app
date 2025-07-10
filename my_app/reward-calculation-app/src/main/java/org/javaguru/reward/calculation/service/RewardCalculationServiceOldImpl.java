package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@ConditionalOnProperty(name = {"transactionalOutbox"},havingValue = "false")
public class RewardCalculationServiceOldImpl implements RewardCalculationService {

    private final JobTypesToPayService jobTypesToPayService;
    private final RewardRepository rewardRepository;
    private final RewardCalculationAndPaymentService rewardCalculationAndPaymentService;

    @Override
    public void calculateRewards(@NonNull List<Employee> employees) {
        employees.forEach(employee ->  findAllRewardsByEmployeeIdAndRewardStatusAndJobType(employee)
                .forEach(reward -> rewardCalculationAndPaymentService.calculateAndPayReward(employee,reward))
        );
    }

    private List<Reward> findAllRewardsByEmployeeIdAndRewardStatusAndJobType(Employee employee){
        return rewardRepository.findByEmployeeIdAndRewardStatusAndJobTypeIn(
                employee.getId(), RewardStatus.NEW, jobTypesToPayService.loadJobTypesToPay());
    }

}
