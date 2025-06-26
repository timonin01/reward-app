package org.javaguru.reward.calculation.service.repositories;

import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Optional<Tariff> findByJobType(JobType jobType);

}
