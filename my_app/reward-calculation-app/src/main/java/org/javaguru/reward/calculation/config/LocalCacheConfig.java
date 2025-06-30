package org.javaguru.reward.calculation.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class LocalCacheConfig {

    public static final String TARIFF_CACHE = "tariffCache";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(LocalCacheConfig.TARIFF_CACHE); // Имя кэша
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS) // TTL = 1 час
                .maximumSize(10));                 // Максимум 10 записей
        return cacheManager;
    }

}
