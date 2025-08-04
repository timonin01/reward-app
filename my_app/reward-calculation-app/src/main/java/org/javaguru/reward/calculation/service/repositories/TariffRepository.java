package org.javaguru.reward.calculation.service.repositories;

import io.micrometer.core.annotation.Timed;
import org.javaguru.reward.calculation.config.cache.LocalCacheConfig;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.Tariff;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, Long> {

    @Timed(value = "jpa_TariffRepository_findByJobType", histogram = true)
    @Cacheable(cacheNames = LocalCacheConfig.TARIFF_CACHE, key = "#p0")
    Optional<Tariff> findByJobType(JobType jobType);

}
