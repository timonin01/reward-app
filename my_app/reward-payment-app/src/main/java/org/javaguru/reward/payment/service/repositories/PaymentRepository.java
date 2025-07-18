package org.javaguru.reward.payment.service.repositories;

import io.micrometer.core.annotation.Timed;
import org.javaguru.reward.payment.service.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {

    @Timed(value = "jpa_PaymentRepository_findByEmployeeId", histogram = true)
    List<Payment> findByEmployeeId(Long employeeId);

    @Timed(value = "jpa_PaymentRepository_findByEmployeeIdAndAmount", histogram = true)
    List<Payment> findByEmployeeIdAndAmount(Long employeeId, BigDecimal amount);

    @Timed(value = "jpa_PaymentRepository_findByEmployeeIdAndRewardId", histogram = true)
    List<Payment> findByEmployeeIdAndRewardId(Long employeeId, Long rewardId);

    @Timed(value = "jpa_PaymentRepository_findByEmployeeIdAndRewardIdAndAmount", histogram = true)
    List<Payment> findByEmployeeIdAndRewardIdAndAmount(Long employeeId, Long rewardId,BigDecimal amount);

}
