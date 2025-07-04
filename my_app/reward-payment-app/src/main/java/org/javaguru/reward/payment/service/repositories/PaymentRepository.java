package org.javaguru.reward.payment.service.repositories;

import org.javaguru.reward.payment.service.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {

    Optional<Payment> findByEmployeeIdAndAmount(Long employeeId, BigDecimal amount);

}
