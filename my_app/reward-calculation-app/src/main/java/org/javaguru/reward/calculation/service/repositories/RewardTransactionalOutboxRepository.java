package org.javaguru.reward.calculation.service.repositories;

import io.micrometer.core.annotation.Timed;
import jakarta.persistence.LockModeType;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardTransactionalOutbox;
import org.javaguru.reward.calculation.service.domain.TransactionalOutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardTransactionalOutboxRepository extends JpaRepository<RewardTransactionalOutbox,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT rto FROM RewardTransactionalOutbox rto WHERE rto.id = :id")
    @Timed(value = "jpa_RewardTransactionalOutboxRepository_findByIdWithLock", histogram = true)
    Optional<RewardTransactionalOutbox> findByIdWithLock(Long id);

    @Timed(value = "jpa_RewardTransactionalOutboxRepository_findByStatus", histogram = true)
    List<RewardTransactionalOutbox> findByStatus(TransactionalOutboxStatus status);

    @Timed(value = "jpa_RewardTransactionalOutboxRepository_findByReward", histogram = true)
    Optional<RewardTransactionalOutbox> findByReward(Reward reward);

}
