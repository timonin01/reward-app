package org.javaguru.reward.calculation.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class LocalCacheConfig {

    public static final String TARIFF_CACHE = "tariffCache";
    public static final String JOBTYPES_CACHE = "jobTypesCache";

    @Bean
    public Caffeine<Object, Object> tariffCacheConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS) // TTL of 1 hour
                .maximumSize(10);
    }

    @Bean
    public Caffeine<Object, Object> jobTypesCacheConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS); // TTL of 1 hour
    }

    @Bean
    public CacheManager cacheManager(@Qualifier("tariffCacheConfig") Caffeine<Object, Object> tariffCacheConfig,
                                     @Qualifier("jobTypesCacheConfig") Caffeine<Object, Object> jobTypesCacheConfig) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // Define caches with names and configurations
        cacheManager.setCaches(Arrays.asList(
                new CaffeineCache(TARIFF_CACHE, tariffCacheConfig.build()),
                new CaffeineCache(JOBTYPES_CACHE, jobTypesCacheConfig.build())
        ));

        return cacheManager;
    }

}
