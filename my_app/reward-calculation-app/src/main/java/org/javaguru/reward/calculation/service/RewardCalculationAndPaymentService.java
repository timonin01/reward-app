package org.javaguru.reward.calculation.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.domain.Tariff;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.TariffRepository;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardCalculationAndPaymentService {

    private final RewardRepository rewardRepository;
    private final TariffRepository tariffRepository;
    private final RewardPaymentClient rewardPaymentClient;

    @Transactional
    public void calculateAndPayReward(Employee employee, Reward reward) {
        Optional<Tariff> tariff = tariffRepository.findByJobType(reward.getJobType());
        if (tariff.isPresent()) {
            BigDecimal amount = calculateAmount(employee, tariff.get());
            rewardPaymentClient.payReward(employee.getId(), amount);
            log.info("Payment sent to " + employee.getFirstName() + " " + employee.getLastName()
                    + ", ID = " + employee.getId() + " with " + amount);
            reward.setRewardStatus(RewardStatus.PAID);
            rewardRepository.save(reward);
        } else {
            log.info("Payment not sent to " + employee.getFirstName() + " " + employee.getLastName()
                    + ", ID = " + employee.getId() + ",because Tariff does not exist");
            reward.setRewardStatus(RewardStatus.NOT_PAID);
            rewardRepository.save(reward);
        }
    }

    private BigDecimal calculateAmount(Employee employee, Tariff tariff){
        return (BigDecimal.ONE.add(employee.getBonusCoefficient()).multiply(tariff.getAmount()))
                .setScale(2, RoundingMode.HALF_UP);
    }

}
