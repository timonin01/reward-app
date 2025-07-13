package org.javaguru.reward.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.payment.service.domain.Payment;
import org.javaguru.reward.payment.service.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void pay(Long employeeId,Long rewardId, BigDecimal amount) {
        if(!checkPaymentInDB(employeeId,rewardId)) {
            Payment payment = new Payment();
            payment.setEmployeeId(employeeId);
            payment.setRewardId(rewardId);
            payment.setAmount(amount);
            paymentRepository.save(payment);
        }
    }

    private boolean checkPaymentInDB(Long employeeId,Long rewardId){
        List<Payment> payments = paymentRepository.findByEmployeeIdAndRewardId(employeeId,rewardId);
        return payments.size() == 1;
    }

}
