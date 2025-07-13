package org.javaguru.reward.calculation.concurrency;

import lombok.AllArgsConstructor;
import org.javaguru.reward.calculation.RewardApplicationAcceptanceTest;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.reward.RewardDTO;

@AllArgsConstructor
public class EmployeeThread extends RewardApplicationAcceptanceTest
        implements Runnable {

    private EmployeeDTO employee;
    private int rewardCount;
    private String jobType;

    @Override
    public void run() {
        for (int i = 1; i <= rewardCount; i++) {
            RewardDTO rewardDTO = createReward(employee.getId(), jobType, "NEW");

            // invoke calculateRewards()
            rewardCalculation();

            // launch transactional outbox job
            launchJob();
        }

    }

}
