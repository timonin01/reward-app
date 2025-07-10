package org.javaguru.reward.calculation.service.job;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.javaguru.reward.calculation.service.domain.RewardTransactionalOutbox;
import org.javaguru.reward.calculation.service.domain.TransactionalOutboxStatus;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.RewardTransactionalOutboxRepository;
import org.javaguru.reward.calculation.service.restclient.RewardPaymentClient;
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
        Reward reward = rewardTransactionalOutbox.getReward();
        rewardPaymentClient.payReward(reward.getEmployeeId(), reward.getAmount());

        // update transactional outbox status
        rewardTransactionalOutbox.setStatus(TransactionalOutboxStatus.PROCESSED);
        rewardTransactionalOutboxRepository.save(rewardTransactionalOutbox);

        // update reward status
        reward.setRewardStatus(RewardStatus.PAID);
        rewardRepository.save(reward);

        log.info("Finish processing rewardTransactionalOutbox with id = " + rewardTransactionalOutbox.getId());
    }

}
