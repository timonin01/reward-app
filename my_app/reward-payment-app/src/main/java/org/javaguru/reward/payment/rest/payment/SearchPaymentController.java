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
import java.util.Optional;

@RestController
@RequestMapping("/api/test/payment")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SearchPaymentController {

    private final PaymentRepository paymentRepository;

    @GetMapping(path = "/search",
            produces = "application/json")
    public SearchPaymentResponse searchPayment(@RequestParam(required = true) Long employeeId,
                                               @RequestParam(required = true) Double amount) {
        PaymentDTO paymentDTO = searchPaymentInDB(employeeId,amount);
        return new SearchPaymentResponse(paymentDTO);
    }

    private PaymentDTO searchPaymentInDB(Long employeeId,Double amount){
        Optional<Payment> payment = paymentRepository.findByEmployeeIdAndAmount(employeeId, BigDecimal.valueOf(amount));
        if(payment.isPresent()){
            return createPaymentDTO(payment.get());
        }
        throw new IllegalArgumentException("Payment not found by employeeId " + employeeId + " and amount " + amount);
    }

    private PaymentDTO createPaymentDTO(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setEmployeeId(payment.getEmployeeId());
        paymentDTO.setAmount(payment.getAmount());
        return paymentDTO;
    }

}
