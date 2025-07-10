package org.javaguru.reward.calculation.service.repositories;

import org.javaguru.reward.calculation.service.domain.RewardTransactionalOutbox;
import org.javaguru.reward.calculation.service.domain.TransactionalOutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardTransactionalOutboxRepository extends JpaRepository<RewardTransactionalOutbox,Long> {

    List<RewardTransactionalOutbox> findByStatus(TransactionalOutboxStatus status);

}
