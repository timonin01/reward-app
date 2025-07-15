package org.javaguru.reward.calculation.rest.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.job.RewardTransactionalOutboxJob;
import org.javaguru.reward.calculation.service.job.batch.RewardTransactionalOutboxBatchJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/reward/transactional-outbox/job")
public class LaunchJobController {

    @Autowired(required = false) private RewardTransactionalOutboxJob job;
    @Autowired(required = false) private RewardTransactionalOutboxBatchJob batchJob;

    @PostMapping(path = "/",
            produces = "application/json")
    public LaunchJobResponse launchJob() {
        if (job != null) {
            job.doJob();
        }
        if (batchJob != null) {
            batchJob.doJob();
        }
        return new LaunchJobResponse();
    }

}
