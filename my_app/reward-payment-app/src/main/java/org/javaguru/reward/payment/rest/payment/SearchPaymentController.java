package org.javaguru.reward.payment.rest.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.payment.service.domain.Payment;
import org.javaguru.reward.payment.service.repositories.PaymentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test/payment")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SearchPaymentController {

    private final PaymentRepository paymentRepository;

    @GetMapping(path = "/search",
            produces = "application/json")
    public SearchPaymentResponse searchPayment(@RequestParam(required = true) Long employeeId,
                                               @RequestParam(required = false) Long rewardId,
                                               @RequestParam(required = false) Double amount) {
        List<PaymentDTO> paymentDTO = searchPaymentInDB(employeeId,rewardId,amount);
        return new SearchPaymentResponse(paymentDTO);
    }

    private List<PaymentDTO> searchPaymentInDB(Long employeeId, Long rewardId,Double amount){
        List<PaymentDTO> payment = getPayments(employeeId,rewardId, amount)
                .stream()
                .map(this::createPaymentDTO)
                .toList();
        if(payment.isEmpty()) throw new IllegalArgumentException("Payment not found by employeeId " + employeeId + " and amount " + amount);
        return payment;
    }

    private List<Payment> getPayments(Long employeeId,Long rewardId,Double amount){
        if(amount == null && rewardId == null){
            return paymentRepository.findByEmployeeId(employeeId);
        }if(amount == null) {
            return paymentRepository.findByEmployeeIdAndRewardId(employeeId,rewardId);
        }
        if(rewardId == null){
            return paymentRepository.findByEmployeeIdAndAmount(employeeId, BigDecimal.valueOf(amount));
        }
        return paymentRepository.findByEmployeeIdAndRewardIdAndAmount(employeeId,rewardId,BigDecimal.valueOf(amount));
    }

    private PaymentDTO createPaymentDTO(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setEmployeeId(payment.getEmployeeId());
        paymentDTO.setRewardId(payment.getRewardId());
        paymentDTO.setAmount(payment.getAmount());
        return paymentDTO;
    }

}
