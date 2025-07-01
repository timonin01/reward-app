package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.config.JobTypesConfig;
import org.javaguru.reward.calculation.service.domain.*;
import org.javaguru.reward.calculation.service.repositories.JobTypesRepository;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.TariffRepository;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Сервис тарификации вознаграждений сотрудникам за дополнительную работу.
 * Каждый сотрудник может выполнять что-либо помимо основной работы - проводить лекции, выступать на конференциях и т.д.
 * Такие действия оплачиваются согласно тарифам, с учетом заслуг сотрудника (личного бонусного коэффициента).
 * Оплата проходит через внешний сервис, вызываемый по REST.
 */

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardCalculationService {

    private final JobTypesToPayService jobTypesToPayService;
    private final RewardRepository rewardRepository;
    private final TariffRepository tariffRepository;
    private final RewardPaymentClient rewardPaymentClient;

    @Transactional
    public void calculateRewards(@NonNull List<Employee> employees) {
        for (Employee employee : employees) {
            List<Reward> rewards = rewardRepository.findByEmployeeIdAndStatusAndJobTypeIn(
                    employee.getId(), RewardStatus.NEW, jobTypesToPayService.loadJobTypesToPay());
            for (Reward reward : rewards) {
                Optional<Tariff> tariff = tariffRepository.findByJobType(reward.getJobType());
                if(tariff.isPresent()) {
                    BigDecimal amount = calculateAmount(employee, tariff.get());
                    rewardPaymentClient.payReward(employee.getId(), amount);
                    log.info("Payment sent to " + employee.getFirstName() + " " + employee.getLastName()
                            + ", ID = " + employee.getId() + " with " + amount);
                    reward.setRewardStatus(RewardStatus.PAID);
                    rewardRepository.save(reward);
                }
                else {
                    log.info("Payment not sent to " + employee.getFirstName() + " " + employee.getLastName()
                            + ", ID = " + employee.getId() + ",because Tariff does not exist");
                    reward.setRewardStatus(RewardStatus.NOT_PAID);
                    rewardRepository.save(reward);
                }
            }
        }
    }

    private BigDecimal calculateAmount(Employee employee, Tariff tariff){
        return (BigDecimal.ONE.add(employee.getBonusCoefficient()).multiply(tariff.getAmount()))
                .setScale(2, RoundingMode.HALF_UP);
    }

}
