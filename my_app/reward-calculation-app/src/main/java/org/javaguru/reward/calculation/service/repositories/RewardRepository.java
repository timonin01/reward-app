package org.javaguru.reward.calculation.service.repositories;

import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Reward;
import org.javaguru.reward.calculation.service.domain.RewardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    Optional<Reward> findById(Long id);

    List<Reward> findByEmployeeIdAndStatusAndJobTypeIn(Long employeeId,
                                                       RewardStatus status,
                                                       Set<JobType> jobTypes);

}
