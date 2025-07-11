package org.javaguru.reward.calculation.rest.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.calculation.service.job.RewardTransactionalOutboxJob;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/reward/transactional-outbox/job")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class LaunchJobController {

    private final RewardTransactionalOutboxJob job;

    @PostMapping(path = "/",
        produces = "application/json")
    public LaunchJobResponse launchJob() {
        job.doJob();
        return new LaunchJobResponse();
    }

}
