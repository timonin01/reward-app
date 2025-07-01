package org.javaguru.reward.calculation;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.reward.RewardDTO;
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
        cleanRewardDb(true, true, true, true);

        // create employees
        EmployeeDTO employeeDTO = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));

        // create rewards
        RewardDTO rewardDTO = createReward(employeeDTO.getId(), "HELP", "NEW");

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));

        // invoke calculateRewards()
        rewardCalculation();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        checkReward(rewardDTO, employeeDTO, "HELP", "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 45.0);
        checkPayment(paymentDTO, employeeDTO, new BigDecimal("45.0"));
    }

    @Test
    public void shouldPayJobType_SPEECH() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

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
        checkReward(rewardDTO, employeeDTO, "SPEECH", "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 30.0);
        checkPayment(paymentDTO, employeeDTO, new BigDecimal("30.0"));
    }

    @Test
    public void shouldPayJobType_LESSON() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

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
        checkReward(rewardDTO, employeeDTO, "LESSON", "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 30.0);
        checkPayment(paymentDTO, employeeDTO, new BigDecimal("30.0"));
    }

    @Test
    public void shouldPayJobType_HELP_twoEmployee() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

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

        // check payments
        PaymentDTO paymentDTO1 = getPayment(employeeDTO1.getId(), 45.0);
        checkPayment(paymentDTO1, employeeDTO1, new BigDecimal("45.0"));

        PaymentDTO paymentDTO2 = getPayment(employeeDTO2.getId(), 45.0);
        checkPayment(paymentDTO2, employeeDTO2, new BigDecimal("45.0"));
    }

    @Test
    public void shouldPayJobType_HELP_LESSON_twoEmployee() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

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
        checkReward(rewardDTO1, employeeDTO1, "HELP", "PAID");

        rewardDTO2 = getReward(rewardDTO2.getId());
        checkReward(rewardDTO2, employeeDTO2, "LESSON", "PAID");

        // check payments
        PaymentDTO paymentDTO1 = getPayment(employeeDTO1.getId(), 45.0);
        checkPayment(paymentDTO1, employeeDTO1, new BigDecimal("45.0"));

        PaymentDTO paymentDTO2 = getPayment(employeeDTO2.getId(), 45.0);
        checkPayment(paymentDTO2, employeeDTO2, new BigDecimal("45.0"));
    }

    @Test
    public void shouldPayJobType_HELP_bonusCoef_Zero() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

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
        checkReward(rewardDTO, employeeDTO, "HELP", "PAID");

        // check payments
        PaymentDTO paymentDTO = getPayment(employeeDTO.getId(), 30.0);
        checkPayment(paymentDTO, employeeDTO, new BigDecimal("30.0"));
    }

    private void checkReward(RewardDTO rewardDTO, EmployeeDTO employeeDTO, String jobType, String status) {
        assertNotNull(rewardDTO);
        assertNotNull(rewardDTO.getId());
        assertEquals(rewardDTO.getEmployeeId(), employeeDTO.getId());
        assertEquals(rewardDTO.getJobType(), jobType);
        assertEquals(rewardDTO.getStatus(), status);
    }

    private void checkPayment(PaymentDTO paymentDTO, EmployeeDTO employeeDTO, BigDecimal amount) {
        assertNotNull(paymentDTO);
        assertNotNull(paymentDTO.getId());
        assertEquals(paymentDTO.getEmployeeId(), employeeDTO.getId());
        assertEquals(0, paymentDTO.getAmount().compareTo(amount));
    }

}