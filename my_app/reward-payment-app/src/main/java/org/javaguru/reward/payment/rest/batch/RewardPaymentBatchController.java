package org.javaguru.reward.payment.rest.batch;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.payment.rest.RewardPaymentRequest;
import org.javaguru.reward.payment.service.RewardPaymentService;
import org.javaguru.reward.payment.service.domain.PaymentStatus;
import org.javaguru.reward.payment.service.repositories.PaymentRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reward/payment/batch")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentBatchController {

    private final RewardPaymentService rewardPaymentService;
    private final PaymentRepository paymentRepository;

    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public RewardPaymentBatchResponse payRewards(@RequestBody RewardPaymentBatchRequest request) {
        List<RewardPaymentStatusDTO> paymentStatuses = new ArrayList<>();
        for (RewardPaymentRequest dto : request.getRewards()) {
            try {
                rewardPaymentService.pay(dto.getEmployeeId(), dto.getRewardId(), dto.getAmount());
                paymentStatuses.add(new RewardPaymentStatusDTO(dto, PaymentStatus.SUCCESS.name()));
            }
            catch (Throwable e) {
                if (isAlreadyExistInDatabase(dto.getEmployeeId(), dto.getRewardId()))
                    paymentStatuses.add(new RewardPaymentStatusDTO(dto, PaymentStatus.SUCCESS.name()));
                else
                    paymentStatuses.add(new RewardPaymentStatusDTO(dto, PaymentStatus.FAIL.name()));
            }
        }
        return new RewardPaymentBatchResponse(paymentStatuses);
    }

    private boolean isAlreadyExistInDatabase(Long employeeId, Long rewardId) {
        var payments = paymentRepository.findByEmployeeIdAndRewardId(employeeId, rewardId);
        return payments.size() == 1;
    }

}
