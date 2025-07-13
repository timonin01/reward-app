package org.javaguru.reward.calculation.service.job;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.*;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.RewardTransactionalOutboxRepository;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentClient;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardTransactionalOutboxProcessingService {

    private final RewardTransactionalOutboxRepository rewardTransactionalOutboxRepository;
    private final RewardRepository rewardRepository;
    private final RewardPaymentClient rewardPaymentClient;

    @Transactional
    public void process(RewardTransactionalOutbox rewardTransactionalOutbox) {
        log.info("Start processing rewardTransactionalOutbox with id = " + rewardTransactionalOutbox.getId());

        // send to reward-payment-app
        try {
            Reward reward = rewardTransactionalOutbox.getReward();
            RewardPaymentResponse paymentResponse = rewardPaymentClient.payReward(reward.getEmployeeId(), reward.getId(), reward.getAmount());

        // update reward status
        if (PaymentStatus.SUCCESS.name().equals(paymentResponse.getStatus())) {
            log.info("Set RewardStatus to PAID");
            reward.setRewardStatus(RewardStatus.PAID);
        } else {
            log.info("Set RewardStatus to NOT_PAID");
            reward.setRewardStatus(RewardStatus.NOT_PAID);
        }
        rewardRepository.save(reward);

        // update transactional outbox status
        rewardTransactionalOutbox.setStatus(TransactionalOutboxStatus.PROCESSED);
        rewardTransactionalOutboxRepository.save(rewardTransactionalOutbox);
        }catch (Throwable e) {
            log.error("Transactional Outbox exception", e);
        }

        log.info("Finish processing rewardTransactionalOutbox with id = " + rewardTransactionalOutbox.getId());
    }

}
