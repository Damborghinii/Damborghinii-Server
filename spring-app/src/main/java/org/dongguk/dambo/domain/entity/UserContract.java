package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.exception.usercontract.UserContractErrorCode;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_contracts")
public class UserContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private EContractRole role;

    @Column(name = "investment")
    private Long investment;

    @Column(name = "stake", precision = 3, scale = 1)
    private BigDecimal stake;

    @Column(name = "repayment_count", nullable = false)
    private Integer repaymentCount;

    @Column(name = "round", nullable = false)
    private Integer round;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private EContractStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contract contract;

    @Builder
    private UserContract(EContractRole role, Long investment,
                         BigDecimal stake, Integer repaymentCount, Integer round,
                         EContractStatus status, User user, Contract contract) {
        this.role = role;
        this.investment = investment;
        this.stake = stake;
        this.repaymentCount = repaymentCount;
        this.round = round;
        this.status = status;
        this.user = user;
        this.contract = contract;
    }

    public static UserContract create(EContractRole role, Long investment,
                                      BigDecimal stake, Integer repaymentCount, Integer round,
                                      EContractStatus status, User user, Contract contract) {
        return UserContract.builder()
                .role(role)
                .investment(investment)
                .stake(stake)
                .repaymentCount(repaymentCount)
                .round(round)
                .status(status)
                .user(user)
                .contract(contract)
                .build();
    }

    public void updateRound() {
        if(this.round > this.getRepaymentCount())
            throw new CustomException(UserContractErrorCode.INVALID_ROUND_OVERFLOW);
        if(round + 1 > this.getRepaymentCount())
            return;
        round += 1;
    }

    public void updateStatus(EContractStatus status) {
        this.status = status;
    }
}
