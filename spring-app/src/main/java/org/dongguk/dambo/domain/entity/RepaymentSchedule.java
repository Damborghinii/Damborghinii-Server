package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "repayment_schedules")
public class RepaymentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "round", nullable = false)
    private Integer round;

    @Column(name = "repayment_date", nullable = false)
    private LocalDate repaymentDate;

    @Column(name = "settlement_date")
    private LocalDate settlementDate;

    @Column(name = "repayment_amount", nullable = false)
    private Long repaymentAmount;

    @Column(name = "late_fee")
    private Long lateFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "repayment_status", length = 50)
    private ERepaymentStatus repaymentStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_contract_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserContract userContract;

    @Builder
    private RepaymentSchedule(Integer round, LocalDate repaymentDate, LocalDate settlementDate, Long repaymentAmount,
                              Long lateFee, ERepaymentStatus repaymentStatus, UserContract userContract) {
        this.round = round;
        this.repaymentDate = repaymentDate;
        this.settlementDate = settlementDate;
        this.repaymentAmount = repaymentAmount;
        this.lateFee = lateFee;
        this.repaymentStatus = repaymentStatus;
        this.userContract = userContract;
    }

    public static RepaymentSchedule create(Integer round, LocalDate repaymentDate, LocalDate settlementDate, Long repaymentAmount,
                                           Long lateFee, ERepaymentStatus repaymentStatus, UserContract userContract) {
        return RepaymentSchedule.builder()
                .round(round)
                .repaymentDate(repaymentDate)
                .settlementDate(settlementDate)
                .repaymentAmount(repaymentAmount)
                .lateFee(lateFee)
                .repaymentStatus(repaymentStatus)
                .userContract(userContract)
                .build();
    }
}
