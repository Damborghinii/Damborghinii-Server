package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "loan_amount")
    private Long loanAmount;

    @Column(name = "repayment_count")
    private Integer repaymentCount;

    @Column(name = "interest_rate", precision = 5, scale = 4)
    private BigDecimal interestRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private EContractStatus status;

    @Column(name = "loan_start_date")
    private LocalDate loanStartDate;

    @Column(name = "loan_end_date")
    private LocalDate loanEndDate;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "copyright_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MusicCopyright musicCopyright;

    @Builder
    private Contract(Long loanAmount, Integer repaymentCount, BigDecimal interestRate, EContractStatus status,
                     LocalDate loanStartDate, LocalDate loanEndDate, MusicCopyright musicCopyright) {
        this.loanAmount = loanAmount;
        this.repaymentCount = repaymentCount;
        this.interestRate = interestRate;
        this.status = status;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.musicCopyright = musicCopyright;
    }

    public static Contract create(Long loanAmount, Integer repaymentCount, BigDecimal interestRate, EContractStatus status,
                                  LocalDate loanStartDate, LocalDate loanEndDate, MusicCopyright copyright) {
        return Contract.builder()
                .loanAmount(loanAmount)
                .repaymentCount(repaymentCount)
                .interestRate(interestRate)
                .status(status)
                .loanStartDate(loanStartDate)
                .loanEndDate(loanEndDate)
                .musicCopyright(copyright)
                .build();
    }
    public void updateContractOnLoan(
            Long loanAmount,
            Integer repaymentCount,
            BigDecimal interestRate,
            LocalDateTime expirationTime
    ) {
        this.loanAmount = loanAmount;
        this.repaymentCount = repaymentCount;
        this.interestRate = interestRate;
        this.status = EContractStatus.INVESTING;
        this.expirationTime = expirationTime;
    }

    public void updateStatus(EContractStatus status) {
        this.status = status;
    }

    public void updateLoanStartDate(LocalDate loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public void updateLoanEndDate(LocalDate loanEndDate) {
        this.loanEndDate = loanEndDate;
    }
}
