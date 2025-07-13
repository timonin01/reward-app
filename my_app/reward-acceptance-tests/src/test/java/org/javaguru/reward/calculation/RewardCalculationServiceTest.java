package org.javaguru.reward.calculation;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.outbox.RewardTransactionalOutboxDTO;
import org.javaguru.rewardapp.reward.RewardDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

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

        // launch transactional outbox job
        launchJob();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        checkReward(rewardDTO, employeeDTO, "HELP", "PAID");

        // check transactional outbox records
        RewardTransactionalOutboxDTO outbox = getRewardTransactionalOutbox(rewardDTO.getId());
        checkOutbox(outbox, rewardDTO);

        // check payments
        List<PaymentDTO> payments = getPayments(employeeDTO.getId(), rewardDTO.getId(), 45.0);
        assertEquals(payments.size(), 1);
        checkPayment(payments.getFirst(), employeeDTO, new BigDecimal("45.0"));
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

        // launch transactional outbox job
        launchJob();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        checkReward(rewardDTO, employeeDTO, "SPEECH", "PAID");

        // check transactional outbox records
        RewardTransactionalOutboxDTO outbox = getRewardTransactionalOutbox(rewardDTO.getId());
        checkOutbox(outbox, rewardDTO);

        // check payments
        List<PaymentDTO> payments = getPayments(employeeDTO.getId(), rewardDTO.getId(), 30.0);
        assertEquals(payments.size(), 1);
        checkPayment(payments.getFirst(), employeeDTO, new BigDecimal("30.0"));
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

        // launch transactional outbox job
        launchJob();

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        checkReward(rewardDTO, employeeDTO, "LESSON", "PAID");

        // check transactional outbox records
        RewardTransactionalOutboxDTO outbox = getRewardTransactionalOutbox(rewardDTO.getId());
        checkOutbox(outbox, rewardDTO);

        // check payments
        List<PaymentDTO> payments = getPayments(employeeDTO.getId(),rewardDTO.getId(), 30.0);
        assertEquals(payments.size(), 1);
        checkPayment(payments.getFirst(), employeeDTO, new BigDecimal("30.0"));
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

        // launch transactional outbox job
        launchJob();

        // check transactional outbox records
        RewardTransactionalOutboxDTO outbox1 = getRewardTransactionalOutbox(rewardDTO1.getId());
        checkOutbox(outbox1, rewardDTO1);

        RewardTransactionalOutboxDTO outbox2 = getRewardTransactionalOutbox(rewardDTO2.getId());
        checkOutbox(outbox2, rewardDTO2);

        // check payments
        List<PaymentDTO> payments1 = getPayments(employeeDTO1.getId(),rewardDTO1.getId(), 45.0);
        assertEquals(payments1.size(), 1);
        checkPayment(payments1.getFirst(), employeeDTO1, new BigDecimal("45.0"));

        List<PaymentDTO> payments2 = getPayments(employeeDTO2.getId(),rewardDTO2.getId(), 45.0);
        assertEquals(payments2.size(), 1);
        checkPayment(payments2.getFirst(), employeeDTO2, new BigDecimal("45.0"));
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

        // launch transactional outbox job
        launchJob();

        // check Reward
        rewardDTO1 = getReward(rewardDTO1.getId());
        checkReward(rewardDTO1, employeeDTO1, "HELP", "PAID");

        rewardDTO2 = getReward(rewardDTO2.getId());
        checkReward(rewardDTO2, employeeDTO2, "LESSON", "PAID");

        // check transactional outbox records
        RewardTransactionalOutboxDTO outbox1 = getRewardTransactionalOutbox(rewardDTO1.getId());
        checkOutbox(outbox1, rewardDTO1);

        RewardTransactionalOutboxDTO outbox2 = getRewardTransactionalOutbox(rewardDTO2.getId());
        checkOutbox(outbox2, rewardDTO2);

        // check payments
        List<PaymentDTO> payments1 = getPayments(employeeDTO1.getId(),rewardDTO1.getId(), 45.0);
        assertEquals(payments1.size(), 1);
        checkPayment(payments1.getFirst(), employeeDTO1, new BigDecimal("45.0"));

        List<PaymentDTO> payments2 = getPayments(employeeDTO2.getId(),rewardDTO2.getId(), 45.0);
        assertEquals(payments2.size(), 1);
        checkPayment(payments2.getFirst(), employeeDTO2, new BigDecimal("45.0"));
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

        // launch transactional outbox job
        launchJob();

        // check transactional outbox records
        RewardTransactionalOutboxDTO outbox = getRewardTransactionalOutbox(rewardDTO.getId());
        checkOutbox(outbox, rewardDTO);

        // check Reward
        rewardDTO = getReward(rewardDTO.getId());
        checkReward(rewardDTO, employeeDTO, "HELP", "PAID");

        // check payments
        List<PaymentDTO> payments = getPayments(employeeDTO.getId(),rewardDTO.getId(), 30.0);
        assertEquals(payments.size(), 1);
        checkPayment(payments.getFirst(), employeeDTO, new BigDecimal("30.0"));
    }

    private void checkReward(RewardDTO rewardDTO, EmployeeDTO employeeDTO, String jobType, String status) {
        assertNotNull(rewardDTO);
        assertNotNull(rewardDTO.getId());
        assertEquals(rewardDTO.getEmployeeId(), employeeDTO.getId());
        assertEquals(rewardDTO.getJobType(), jobType);
        assertEquals(rewardDTO.getRewardStatus(), status);
    }

    private void checkPayment(PaymentDTO paymentDTO, EmployeeDTO employeeDTO, BigDecimal amount) {
        assertNotNull(paymentDTO);
        assertNotNull(paymentDTO.getId());
        assertEquals(paymentDTO.getEmployeeId(), employeeDTO.getId());
        assertEquals(0, paymentDTO.getAmount().compareTo(amount));
    }

    private void checkOutbox(RewardTransactionalOutboxDTO outbox, RewardDTO rewardDTO) {
        assertNotNull(outbox);
        assertNotNull(outbox.getId());
        assertEquals(outbox.getRewardId(), rewardDTO.getId());
        assertEquals(outbox.getStatus(), "PROCESSED");
    }

}