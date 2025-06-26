package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.Employee;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.Tariff;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.TariffRepository;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private final RewardRepository rewardRepository;
    private final TariffRepository tariffRepository;
    private final RewardPaymentClient rewardPaymentClient;

    @Transactional
    public void calculateRewards(List<Employee> employees) {
        for (Employee employee : employees) {
            List<Reward> rewards = rewardRepository.findByEmployeeId(employee.getId());
            for (Reward reward : rewards) {
                if (List.of(JobType.SPEECH, JobType.LESSON, JobType.HELP).contains(reward.getJobType())) {
                    Tariff tariff = tariffRepository.findByJobType(reward.getJobType()).get();
                    double amount = (1 + employee.getBonusCoefficient()) * tariff.getAmount();
                    rewardPaymentClient.payReward(employee.getId(), amount);
                    log.info("Payment sent to "+ employee.getFirstName()+" "+employee.getLastName()
                            +", ID = " +employee.getId()+" with "+amount);
                    reward.setStatus("PAID");
                    rewardRepository.save(reward);
                }
            }
        }
    }

}
