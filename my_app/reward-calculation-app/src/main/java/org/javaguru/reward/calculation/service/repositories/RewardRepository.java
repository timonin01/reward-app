package org.javaguru.reward.calculation.service.repositories;

import io.micrometer.core.annotation.Timed;
import jakarta.persistence.LockModeType;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    @Timed(value = "jpa_RewardRepository_findByIdWithLock", histogram = true)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Reward r WHERE r.id = :id")
    Optional<Reward> findByIdWithLock(Long id);

    @Timed(value = "jpa_RewardRepository_findByEmployeeIdAndRewardStatusAndJobTypeIn", histogram = true)
    List<Reward> findByEmployeeIdAndRewardStatusAndJobTypeIn(Long employeeId,
                                                       RewardStatus status,
                                                       List<JobType> jobTypes);

}
