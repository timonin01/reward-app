package org.javaguru.reward.payment.rest.cleandb;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.reward.payment.service.repositories.PaymentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/cleandb")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CleanDBController {

    private final PaymentRepository paymentRepository;

    @PostMapping(path = "/",
        consumes = "application/json",
        produces = "application/json")
    @Transactional
    public CleanDBResponse cleanDB(@RequestBody CleanDBRequest request){
        CleanDBResponse response = new CleanDBResponse();

        if (request.isCleanPayment()){
            paymentRepository.deleteAll();
            response.setCleanPayment(true);
        }
        return response;
    }

}
