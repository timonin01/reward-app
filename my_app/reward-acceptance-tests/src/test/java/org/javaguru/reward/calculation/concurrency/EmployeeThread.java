package org.javaguru.reward.calculation.concurrency;

import lombok.AllArgsConstructor;
import org.javaguru.reward.calculation.RewardApplicationAcceptanceTest;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.reward.RewardDTO;

@AllArgsConstructor
public class EmployeeThread implements Runnable {
    private final RewardApplicationAcceptanceTest test;
    private final EmployeeDTO employee;
    private final int rewardCount;
    private final String jobType;

    @Override
    public void run() {
        for (int i = 1; i <= rewardCount; i++) {
            RewardDTO rewardDTO = test.createReward(employee.getId(), jobType, "NEW");
            test.rewardCalculation();
            test.launchJob();
        }
    }
}