package org.javaguru.reward.calculation.service.repositories;

import io.micrometer.core.annotation.Timed;
import org.javaguru.reward.calculation.config.LocalCacheConfig;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.domain.JobTypes;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface JobTypesRepository extends JpaRepository<JobTypes,Long> {

    @Timed(value = "jpa_JobTypesRepository_findByJobType", histogram = true)
    Optional<JobTypes> findByJobType(JobType jobType);

    @Cacheable(cacheNames = LocalCacheConfig.JOBTYPES_CACHE, key = "#p0")
    @Query("SELECT DISTINCT j.jobType FROM job_types j")
    @Timed(value = "jpa_JobTypesRepository_findAllDistinctJobTypes", histogram = true)
    List<JobType> findAllDistinctJobTypes();

}
