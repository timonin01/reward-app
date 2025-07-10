package org.javaguru.reward.calculation.service.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reward_transactional_outbox")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardTransactionalOutbox {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionalOutboxStatus status;

}
