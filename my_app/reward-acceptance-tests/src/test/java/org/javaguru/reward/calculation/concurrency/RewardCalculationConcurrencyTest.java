package org.javaguru.reward.calculation.concurrency;

import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.reward.calculation.RewardApplicationAcceptanceTest;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Disabled
public class RewardCalculationConcurrencyTest
        extends RewardApplicationAcceptanceTest {

    private static final int REWARD_COUNT_PER_EMPLOYEE = 100;

    @Test
    public void twoThreadTest() throws InterruptedException {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));
        createTariff("SPEECH", BigDecimal.valueOf(20.0));

        createAllowedToPayJobType("HELP");
        createAllowedToPayJobType("SPEECH");

        EmployeeDTO employee1 = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));
        Thread thread1 = new Thread(new EmployeeThread(
                employee1, REWARD_COUNT_PER_EMPLOYEE, "HELP"
        ));

        EmployeeDTO employee2 = createEmployee("Mister", "Y", BigDecimal.valueOf(0.5));
        Thread thread2 = new Thread(new EmployeeThread(
                employee2, REWARD_COUNT_PER_EMPLOYEE, "SPEECH"
        ));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        List<PaymentDTO> payments1 = getPayments(employee1.getId());
        assertEquals(payments1.size(), REWARD_COUNT_PER_EMPLOYEE);

        List<PaymentDTO> payments2 = getPayments(employee2.getId());
        assertEquals(payments2.size(), REWARD_COUNT_PER_EMPLOYEE);
    }

    @Test
    public void threeThreadTest() throws InterruptedException {
        // clean db
        cleanPaymentDb(true);
        cleanRewardDb(true, true, true, true);

        // create tariffs
        createTariff("HELP", BigDecimal.valueOf(30.0));
        createTariff("SPEECH", BigDecimal.valueOf(20.0));
        createTariff("LESSON", BigDecimal.valueOf(10.0));

        createAllowedToPayJobType("HELP");
        createAllowedToPayJobType("SPEECH");
        createAllowedToPayJobType("LESSON");

        EmployeeDTO employee1 = createEmployee("Mister", "X", BigDecimal.valueOf(0.5));
        Thread thread1 = new Thread(new EmployeeThread(
                employee1, REWARD_COUNT_PER_EMPLOYEE, "HELP"
        ));

        EmployeeDTO employee2 = createEmployee("Mister", "Y", BigDecimal.valueOf(0.5));
        Thread thread2 = new Thread(new EmployeeThread(
                employee2, REWARD_COUNT_PER_EMPLOYEE, "SPEECH"
        ));

        EmployeeDTO employee3 = createEmployee("Mister", "Z", BigDecimal.valueOf(0.5));
        Thread thread3 = new Thread(new EmployeeThread(
                employee3, REWARD_COUNT_PER_EMPLOYEE, "LESSON"
        ));

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        List<PaymentDTO> payments1 = getPayments(employee1.getId());
        assertEquals(payments1.size(), REWARD_COUNT_PER_EMPLOYEE);

        List<PaymentDTO> payments2 = getPayments(employee2.getId());
        assertEquals(payments2.size(), REWARD_COUNT_PER_EMPLOYEE);

        List<PaymentDTO> payments3 = getPayments(employee3.getId());
        assertEquals(payments3.size(), REWARD_COUNT_PER_EMPLOYEE);
    }

}
