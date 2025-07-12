package org.javaguru.reward.payment.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaguru.reward.payment.service.RewardPaymentService;
import org.javaguru.reward.payment.service.domain.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reward/payment")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentController {

    private final RewardPaymentService rewardPaymentService;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public RewardPaymentResponse payReward(@RequestBody RewardPaymentRequest request) {
        try {
            rewardPaymentService.pay(request.getEmployeeId(), request.getAmount());
        } catch (Throwable e) {
            log.info("Send RewardPaymentResponse with PaymentStatus.FAIL");
            return new RewardPaymentResponse(PaymentStatus.FAIL.name());
        }
        log.info("Send RewardPaymentResponse with PaymentStatus.SUCCESS");
        return new RewardPaymentResponse(PaymentStatus.SUCCESS.name());
    }

}
