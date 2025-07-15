package org.javaguru.reward.calculation.service.job.batch;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.*;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.RewardTransactionalOutboxRepository;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentClient;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentResponse;
import org.javaguru.reward.calculation.service.restclient.batch.RewardPaymentBatchClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardTransactionalOutboxBatchProcessingService {

    private final RewardTransactionalOutboxRepository rewardTransactionalOutboxRepository;
    private final RewardRepository rewardRepository;
    private final RewardPaymentBatchClient rewardPaymentBatchClient;

    @Transactional
    public void process() {
        try {
            List<RewardTransactionalOutbox> rewardOutboxes = rewardTransactionalOutboxRepository
                    .findByStatus(TransactionalOutboxStatus.NEW)
                    .stream()
                    .map(outbox -> rewardTransactionalOutboxRepository.findByIdWithLock(outbox.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            log.info("rewardOutboxes list size = " + rewardOutboxes.size());

            List<Reward> rewardsToPay = rewardOutboxes.stream()
                    .map(RewardTransactionalOutbox::getReward)
                    .toList();

            log.info("rewards list size = " + rewardsToPay.size());

            Map<Reward, String> rewardStatuses = rewardPaymentBatchClient.payRewards(rewardsToPay);

            log.info("rewardStatuses map size = " + rewardStatuses.size());

            for (Reward reward : rewardStatuses.keySet()) {
                log.info("PROCESS REWARD!! rewardId = " + reward.getId());
                String paymentStatus = rewardStatuses.get(reward);
                if (PaymentStatus.SUCCESS.name().equals(paymentStatus)) {
                    reward.setRewardStatus(RewardStatus.PAID);
                } else {
                    reward.setRewardStatus(RewardStatus.NOT_PAID);
                }
                rewardRepository.save(reward);
                log.info("PROCESS REWARD END!! rewardId = " + reward.getId());
            }

            // update transactional outbox status
            rewardOutboxes.forEach(rewardTransactionalOutbox -> {
                rewardTransactionalOutbox.setStatus(TransactionalOutboxStatus.PROCESSED);
                rewardTransactionalOutboxRepository.save(rewardTransactionalOutbox);
            });
        } catch (Throwable e) {
            log.error("Transactional Outbox exception", e);
        }
    }

}
