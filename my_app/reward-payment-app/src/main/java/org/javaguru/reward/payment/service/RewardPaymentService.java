package org.javaguru.reward.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.payment.service.domain.Payment;
import org.javaguru.reward.payment.service.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RewardPaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public void pay(Long employeeId, Double amount) {
        Payment payment = new Payment();
        payment.setEmployeeId(employeeId);
        payment.setAmount(amount);
        paymentRepository.save(payment);
    }

}
