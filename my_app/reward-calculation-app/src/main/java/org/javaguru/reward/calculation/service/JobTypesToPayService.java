package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.calculation.config.JobTypesConfig;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.repositories.JobTypesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class JobTypesToPayService {

    private static final String REDIS_CACHE_KEY = "jobTypesToPay";
    private static final long DEFAULT_CACHE_TTL_HOURS = 1;

    private final JobTypesRepository jobTypesRepository;
    private final JobTypesConfig jobTypesConfig;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${job.types.to.pay.loading}")
    private String jobTypesToPayLoading;

    @Value("${job.types.cache.ttl.hours:1}")
    private long cacheTtlHours;

    public List<JobType> loadJobTypesToPay() {
        try {
            // Пытаемся получить данные из Redis
            List<JobType> cachedTypes = getFromCache();
            if (cachedTypes != null && !cachedTypes.isEmpty()) {
                log.debug("Loaded job types from cache");
                return cachedTypes;
            }

            // Если в кеше нет, загружаем из выбранного источника
            List<JobType> jobTypes = loadFromSource();
            if (jobTypes == null || jobTypes.isEmpty()) {
                log.warn("No job types loaded from source");
                return Collections.emptyList();
            }

            // Сохраняем в Redis с TTL
            saveToCache(jobTypes);
            return jobTypes;

        } catch (Exception e) {
            log.error("Error loading job types to pay", e);
            return loadFromSource(); // Fallback to direct source loading
        }
    }

    private List<JobType> getFromCache() {
        try {
            Object cached = redisTemplate.opsForValue().get(REDIS_CACHE_KEY);
            if (cached instanceof List) {
                return (List<JobType>) cached;
            }
            return null;
        } catch (Exception e) {
            log.warn("Failed to get job types from cache", e);
            return null;
        }
    }

    private void saveToCache(List<JobType> jobTypes) {
        try {
            redisTemplate.opsForValue().set(
                    REDIS_CACHE_KEY,
                    jobTypes,
                    cacheTtlHours > 0 ? cacheTtlHours : DEFAULT_CACHE_TTL_HOURS,
                    TimeUnit.HOURS
            );
            log.debug("Saved job types to cache");
        } catch (Exception e) {
            log.warn("Failed to save job types to cache", e);
        }
    }

    private List<JobType> loadFromSource() {
        try {
            switch (jobTypesToPayLoading) {
                case "CONSTANT":
                    return List.of(JobType.SPEECH, JobType.LESSON, JobType.HELP);
                case "PROPERTIES":
                    return jobTypesConfig.getJobTypes() != null ?
                            jobTypesConfig.getJobTypes() : Collections.emptyList();
                case "DATABASE":
                    return jobTypesRepository.findAllDistinctJobTypes();
                default:
                    log.error("Unsupported load type: {}", jobTypesToPayLoading);
                    throw new IllegalStateException("Unsupported load type: " + jobTypesToPayLoading);
            }
        } catch (Exception e) {
            log.error("Error loading job types from source", e);
            return Collections.emptyList();
        }
    }

    public void refreshCache() {
        try {
            redisTemplate.delete(REDIS_CACHE_KEY);
            log.info("Job types cache refreshed");
        } catch (Exception e) {
            log.error("Failed to refresh job types cache", e);
        }
    }
}