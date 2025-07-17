package org.javaguru.reward.calculation.rest.reward.outbox;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardTransactionalOutbox;
import org.javaguru.reward.calculation.service.repositories.RewardRepository;
import org.javaguru.reward.calculation.service.repositories.RewardTransactionalOutboxRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/reward/transactional-outbox/getByRewardId")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GetRewardTransactionalOutboxController {

    private final RewardTransactionalOutboxRepository rewardTransactionalOutboxRepository;
    private final RewardRepository rewardRepository;

    @GetMapping(path = "/{rewardId}",
            produces = "application/json")
    public GetRewardTransactionalOutboxResponse getByRewardId(@PathVariable Long rewardId) {
        Reward reward = rewardRepository.findById(rewardId).get();
        return rewardTransactionalOutboxRepository.findByReward(reward)
                .map(this::createRewardTransactionalOutboxDTO)
                .map(GetRewardTransactionalOutboxResponse::new)
                .orElseThrow(() -> new IllegalArgumentException("RewardTransactionalOutbox not found by rewardId " + rewardId));
    }

    private RewardTransactionalOutboxDTO createRewardTransactionalOutboxDTO(RewardTransactionalOutbox outbox) {
        RewardTransactionalOutboxDTO dto = new RewardTransactionalOutboxDTO();
        dto.setId(outbox.getId());
        dto.setRewardId(outbox.getReward().getId());
        dto.setStatus(outbox.getStatus().name());
        return dto;
    }

}
