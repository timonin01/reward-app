package org.javaguru.reward.calculation.service.job.batch;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardTransactionalOutboxBatchJob {

    private final RewardTransactionalOutboxBatchProcessingService rewardTransactionalOutboxProcessingService;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void doJob() {
        log.info("RewardTransactionalOutboxBatchJob started");

        rewardTransactionalOutboxProcessingService.process();

        log.info("RewardTransactionalOutboxBatchJob finished");
    }

}
