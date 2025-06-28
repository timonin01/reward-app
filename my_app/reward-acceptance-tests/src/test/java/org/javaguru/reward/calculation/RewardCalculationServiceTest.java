package org.javaguru.reward.calculation;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.reward.RewardDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Do not auto invoked when Gradle.build()")
class RewardCalculationServiceTest extends RewardApplicationAcceptanceTest {

    @Test
    public void shouldPayJobType_HELP() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));

        // create rewards
        RewardDTO rewardDTO = createReward(employeeDTO.getId(), "HELP", "NEW");

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 45.0);
        assertNotNull(paymentDTO);

        // check reward
        rewardDTO = getReward(rewardDTO.getId());
        assertEquals(rewardDTO.getStatus(), "PAID");
    }

    @Test
    public void shouldPayJobType_SPEECH() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));

        // create rewards
        RewardDTO rewardDTO = createReward(employeeDTO.getId(), "SPEECH", "NEW");

        // create tariffs
        createTariff("SPEECH", BigDecimal.valueOf(20.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        assertEquals(rewardDTO.getStatus(), "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 30.0);
        assertNotNull(paymentDTO);
    }

    @Test
    public void shouldPayJobType_LESSON() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));

        // create rewards
        RewardDTO rewardDTO = createReward(employeeDTO.getId(), "LESSON", "NEW");

        // create tariffs
        createTariff("LESSON", BigDecimal.valueOf(20.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        assertEquals(rewardDTO.getStatus(), "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 30.0);
        assertNotNull(paymentDTO);
    }

    @Test
    public void shouldPayJobType_HELP_twoEmployee() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO1 = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));
        EmployeeDTO employeeDTO2 = createEmployee("Mister", "Y", BigDecimal.valueOf(0.5));

        // create rewards
        RewardDTO rewardDTO1 = createReward(employeeDTO1.getId(), "HELP", "NEW");
        RewardDTO rewardDTO2 = createReward(employeeDTO2.getId(), "HELP", "NEW");

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check Reward
        rewardDTO1 = getReward(rewardDTO1.getId());
        rewardDTO2 = getReward(rewardDTO2.getId());
        assertEquals(rewardDTO1.getStatus(), "PAID");
        assertEquals(rewardDTO2.getStatus(), "PAID");

        // check payments
        PaymentDTO paymentDTO1 = getPayment(employeeDTO1.getId(), 45.0);
        PaymentDTO paymentDTO2 = getPayment(employeeDTO2.getId(), 45.0);
        assertNotNull(paymentDTO1);
        assertNotNull(paymentDTO2);
    }

    @Test
    public void shouldPayJobType_HELP_LESSON_twoEmployee() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO1 = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));
        EmployeeDTO employeeDTO2 = createEmployee("Mister", "Y", BigDecimal.valueOf(0.5));

        // create rewards
        RewardDTO rewardDTO1 = createReward(employeeDTO1.getId(), "HELP", "NEW");
        RewardDTO rewardDTO2 = createReward(employeeDTO2.getId(), "LESSON", "NEW");

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));
        createTariff("LESSON", BigDecimal.valueOf(30.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check Reward
        rewardDTO1 = getReward(rewardDTO1.getId());
        rewardDTO2 = getReward(rewardDTO2.getId());
        assertEquals(rewardDTO1.getStatus(), "PAID");
        assertEquals(rewardDTO2.getStatus(), "PAID");

        // check payments
        PaymentDTO paymentDTO1 = getPayment(employeeDTO1.getId(), 45.0);
        PaymentDTO paymentDTO2 = getPayment(employeeDTO2.getId(), 45.0);
        assertNotNull(paymentDTO1);
        assertNotNull(paymentDTO2);
    }

    @Test
    public void shouldPayJobType_HELP_bonusCoef_Zero() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO = createEmployee("Mister", "X", BigDecimal.valueOf(0.0));

        // create rewards
        RewardDTO rewardDTO = createReward(employeeDTO.getId(), "HELP", "NEW");

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        assertEquals(rewardDTO.getStatus(), "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 30.0);
        assertNotNull(paymentDTO);
    }

}