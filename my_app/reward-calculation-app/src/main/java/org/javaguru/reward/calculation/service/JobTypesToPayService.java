package org.javaguru.reward.calculation.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.config.JobTypesConfig;
import org.javaguru.reward.calculation.service.domain.JobType;
import org.javaguru.reward.calculation.service.repositories.JobTypesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class JobTypesToPayService {

    private static final String REDIS_CACHE_KEY = "jobTypesToPay";
    private static final long CACHE_TTL_HOURS = 1;

    private final JobTypesRepository jobTypesRepository;
    private final JobTypesConfig jobTypesConfig;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${job.types.to.pay.loading}")
    private String jobTypesToPayLoading;

    public List<JobType> loadJobTypesToPay() {
        // Пытаемся получить данные из Redis
        List<JobType> cachedTypes = (List<JobType>) redisTemplate.opsForValue().get(REDIS_CACHE_KEY);
        if (cachedTypes != null) {
            return cachedTypes;
        }

        // Если в кеше нет, загружаем из выбранного источника
        List<JobType> jobTypes = loadFromSource();

        // Сохраняем в Redis с TTL
        redisTemplate.opsForValue().set(
                REDIS_CACHE_KEY,
                jobTypes,
                CACHE_TTL_HOURS,
                TimeUnit.HOURS
        );

        return jobTypes;
    }

    private List<JobType> loadFromSource() {
        switch (jobTypesToPayLoading) {
            case "CONSTANT":
                return List.of(JobType.SPEECH, JobType.LESSON, JobType.HELP);
            case "PROPERTIES":
                return jobTypesConfig.getJobTypes();
            case "DATABASE":
                return jobTypesRepository.findAllDistinctJobTypes();
            default:
                throw new IllegalStateException("Unsupported load type!");
        }
    }

    // Метод для принудительного обновления кеша
    public void refreshCache() {
        redisTemplate.delete(REDIS_CACHE_KEY);
    }
}