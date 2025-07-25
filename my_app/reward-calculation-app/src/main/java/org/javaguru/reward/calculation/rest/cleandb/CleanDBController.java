package org.javaguru.reward.calculation.rest.cleandb;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.config.LocalCacheConfig;
import org.javaguru.reward.calculation.service.repositories.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/cleandb")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CleanDBController {

    private final EmployeeRepository employeeRepository;
    private final RewardRepository rewardRepository;
    private final TariffRepository tariffRepository;
    private final JobTypesRepository jobTypesRepository;
    private final RewardTransactionalOutboxRepository rewardTransactionalOutboxRepository;

    @PostMapping(path = "/",
        consumes = "application/json",
        produces = "application/json")
    @Transactional
    @CacheEvict(cacheNames = LocalCacheConfig.TARIFF_CACHE, allEntries = true)
    public CleanDBResponse cleanDB(@RequestBody CleanDBRequest request){
        CleanDBResponse response = new CleanDBResponse();

        if(request.isCleanJobTypes()){
            jobTypesRepository.deleteAll();
            response.setJobTypesDeleted(true);
        }

        if (request.isCleanEmployee()) {
            rewardRepository.deleteAll();
            employeeRepository.deleteAll();
            response.setEmployeeDeleted(true);
        }

        if (request.isCleanReward()) {
//            rewardTransactionalOutboxRepository.deleteAll();
            rewardRepository.deleteAll();
            response.setRewardDeleted(true);
        }

        if (request.isCleanTariff()) {
            tariffRepository.deleteAll();
            response.setTariffDeleted(true);
        }
        return response;
    }

}
