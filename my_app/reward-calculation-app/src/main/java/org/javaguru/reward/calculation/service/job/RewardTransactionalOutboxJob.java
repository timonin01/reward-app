package org.javaguru.reward.calculation.service.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.domain.TransactionalOutboxStatus;
import org.javaguru.reward.calculation.service.repositories.RewardTransactionalOutboxRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
//@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardTransactionalOutboxJob {

    private final RewardTransactionalOutboxProcessingService rewardTransactionalOutboxProcessingService;
    private final RewardTransactionalOutboxRepository rewardTransactionalOutboxRepository;

//    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void doJob() {
        log.info("RewardTransactionalOutboxJob started");

        rewardTransactionalOutboxRepository
                .findByStatus(TransactionalOutboxStatus.NEW)
                .forEach(rewardTransactionalOutbox ->
                        rewardTransactionalOutboxProcessingService.process(rewardTransactionalOutbox.getId()));


        log.info("RewardTransactionalOutboxJob finished");
    }
}
