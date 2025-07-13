package org.javaguru.reward.calculation.service.repositories;

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
    Optional<RewardTransactionalOutbox> findByIdWithLock(Long id);

    List<RewardTransactionalOutbox> findByStatus(TransactionalOutboxStatus status);

    Optional<RewardTransactionalOutbox> findByReward(Reward reward);

}
