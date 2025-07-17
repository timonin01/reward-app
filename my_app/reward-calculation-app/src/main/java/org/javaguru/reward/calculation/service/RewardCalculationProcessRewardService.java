package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.*;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.RewardTransactionalOutboxRepository;
import org.javaguru.reward.calculation.service.repositories.TariffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardCalculationProcessRewardService {

    private final RewardRepository rewardRepository;
    private final TariffRepository tariffRepository;
    private final RewardTransactionalOutboxRepository rewardTransactionalOutboxRepository;

    @Transactional
    public void processReward(Employee employee, Long rewardId){
        Reward reward = rewardRepository.findByIdWithLock(rewardId).get();
        Optional<Tariff> tariff = tariffRepository.findByJobType(reward.getJobType());
        if (tariff.isPresent()) {
            BigDecimal amount = calculateAmount(employee, tariff.get());

            reward.setAmount(amount);
            reward.setRewardStatus(RewardStatus.IN_PROGRESS);

            RewardTransactionalOutbox rewardTransactionalOutbox = new RewardTransactionalOutbox();
            rewardTransactionalOutbox.setReward(reward);
            rewardTransactionalOutbox.setStatus(TransactionalOutboxStatus.NEW);
            rewardTransactionalOutboxRepository.save(rewardTransactionalOutbox);

            log.info("Payment in progress by " + employee.getFirstName() + " " + employee.getLastName()
                    + ", ID = " + employee.getId() + " with " + amount);
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
