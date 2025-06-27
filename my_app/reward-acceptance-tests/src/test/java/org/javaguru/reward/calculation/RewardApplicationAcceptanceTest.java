package org.javaguru.reward.calculation;

import org.javaguru.paymentapp.cleandb.CleanPaymentDbResponse;
import org.javaguru.paymentapp.payment.PaymentDTO;
import org.javaguru.reward.calculation.actions.paymentapp.CleanPaymentDatabaseAction;
import org.javaguru.reward.calculation.actions.paymentapp.SearchPaymentAction;
import org.javaguru.reward.calculation.actions.rewardapp.CleanRewardDatabaseAction;
import org.javaguru.reward.calculation.actions.rewardapp.CreateEmployeeAction;
import org.javaguru.reward.calculation.actions.rewardapp.CreateRewardAction;
import org.javaguru.reward.calculation.actions.rewardapp.CreateTariffAction;
import org.javaguru.reward.calculation.actions.rewardapp.GetRewardAction;
import org.javaguru.reward.calculation.actions.rewardapp.RewardCalculationAction;
import org.javaguru.rewardapp.cleandb.CleanRewardDbResponse;
import org.javaguru.rewardapp.employee.EmployeeDTO;
import org.javaguru.rewardapp.reward.RewardDTO;
import org.javaguru.rewardapp.tariff.TariffDTO;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

public abstract class RewardApplicationAcceptanceTest {

    private CleanRewardDatabaseAction cleanRewardDatabaseAction;
    private CleanPaymentDatabaseAction cleanPaymentDatabaseAction;

    private CreateEmployeeAction createEmployeeAction;
    private CreateRewardAction createRewardAction;
    private CreateTariffAction createTariffAction;
    private RewardCalculationAction rewardCalculationAction;
    private GetRewardAction getRewardAction;

    private SearchPaymentAction searchPaymentAction;

    @BeforeEach
    public void setup() {
        cleanRewardDatabaseAction = new CleanRewardDatabaseAction();
        cleanPaymentDatabaseAction = new CleanPaymentDatabaseAction();

        createEmployeeAction = new CreateEmployeeAction();
        createRewardAction = new CreateRewardAction();
        createTariffAction = new CreateTariffAction();
        rewardCalculationAction = new RewardCalculationAction();
        getRewardAction = new GetRewardAction();

        searchPaymentAction = new SearchPaymentAction();
    }

    public CleanPaymentDbResponse cleanPaymentDb(boolean cleanPayment) {
        return cleanPaymentDatabaseAction.cleanDb(cleanPayment);
    }

    public CleanRewardDbResponse cleanRewardDb(boolean cleanEmployee,
                                               boolean cleanReward,
                                               boolean cleanTariff) {
        return cleanRewardDatabaseAction.cleanRewardDb(cleanEmployee, cleanReward, cleanTariff);
    }

    public EmployeeDTO createEmployee(String firstName,
                                      String lastName,
                                      BigDecimal bonusCoefficient) {
        return createEmployeeAction.createEmployee(firstName, lastName, bonusCoefficient);
    }

    public RewardDTO createReward(Long employeeId,
                                  String jobType,
                                  String status) {
        return createRewardAction.createReward(employeeId, jobType, status);
    }

    public TariffDTO createTariff(String jobType, BigDecimal amount) {
        return createTariffAction.createTariff(jobType, amount);
    }

    public void rewardCalculation() {
        rewardCalculationAction.calculateRewards();
    }

    public PaymentDTO getPayment(Long employeeId, Double amount) {
        return searchPaymentAction.getPayment(employeeId, amount);
    }

    public RewardDTO getReward(Long rewardId) {
        return getRewardAction.getReward(rewardId);
    }
}
