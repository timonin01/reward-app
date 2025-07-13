package org.javaguru.paymentapp.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPaymentResponse {

    private List<PaymentDTO> paymentsDTO;

}
