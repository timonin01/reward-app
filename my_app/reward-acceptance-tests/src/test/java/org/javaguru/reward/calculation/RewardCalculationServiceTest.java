package org.javaguru.reward.calculation;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.reward.RewardDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Do not auto invoked when Gradle.build()")
class RewardCalculationServiceTest extends RewardApplicationAcceptanceTest {

    @Test
    public void firstTest() {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true);

        // create employees
        EmployeeDTO employeeDTO1 = createEmployee("Mister", "X", 0.5);

        // create rewards
        RewardDTO rewardDTO1 = createReward(employeeDTO1.getId(), "HELP", null);
        RewardDTO rewardDTO2 = createReward(employeeDTO1.getId(), "SPEECH", null);
        RewardDTO rewardDTO3 = createReward(employeeDTO1.getId(), "LESSON", null);

        // create tariffs
        createTariff("LESSON", 10.0);
        createTariff("SPEECH", 20.0);
        createTariff("HELP", 30.0);

        // invoke calculateRewards()
        rewardCalculation();

        // check rewards
        rewardDTO1 = getReward(rewardDTO1.getId());
        assertNotNull(rewardDTO1.getId());
        assertEquals(rewardDTO1.getEmployeeId(), employeeDTO1.getId());
        assertEquals(rewardDTO1.getJobType(), "HELP");
        assertEquals(rewardDTO1.getStatus(), "PAID");

        rewardDTO2 = getReward(rewardDTO2.getId());
        assertNotNull(rewardDTO2.getId());
        assertEquals(rewardDTO2.getEmployeeId(), employeeDTO1.getId());
        assertEquals(rewardDTO2.getJobType(), "SPEECH");
        assertEquals(rewardDTO2.getStatus(), "PAID");

        rewardDTO3 = getReward(rewardDTO3.getId());
        assertNotNull(rewardDTO3.getId());
        assertEquals(rewardDTO3.getEmployeeId(), employeeDTO1.getId());
        assertEquals(rewardDTO3.getJobType(), "LESSON");
        assertEquals(rewardDTO3.getStatus(), "PAID");

        // check payments
        PaymentDTO paymentDTO1 = getPayment(employeeDTO1.getId(), 15.0);
        assertNotNull(paymentDTO1.getId());
        assertEquals(paymentDTO1.getEmployeeId(), employeeDTO1.getId());
        assertEquals(paymentDTO1.getAmount(), 15.0, 0.0001);

        PaymentDTO paymentDTO2 = getPayment(employeeDTO1.getId(), 30.0);
        assertNotNull(paymentDTO2.getId());
        assertEquals(paymentDTO2.getEmployeeId(), employeeDTO1.getId());
        assertEquals(paymentDTO2.getAmount(), 30.0, 0.0001);

        PaymentDTO paymentDTO3 = getPayment(employeeDTO1.getId(), 45.0);
        assertNotNull(paymentDTO3.getId());
        assertEquals(paymentDTO3.getEmployeeId(), employeeDTO1.getId());
        assertEquals(paymentDTO3.getAmount(), 45.0, 0.0001);
    }

}