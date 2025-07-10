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


public interface RewardCalculationService {

    void calculateRewards(List<Employee> employees);

}
