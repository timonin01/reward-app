package org.javaguru.reward.calculation.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rewards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reward {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false)
    private JobType jobType;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_status",nullable = false)
    private RewardStatus rewardStatus;

    @Column(name = "amount")
    private BigDecimal amount;

    // для каскадного удаления
    @OneToMany(mappedBy = "reward", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RewardTransactionalOutbox> outboxEntries = new ArrayList<>();

}
