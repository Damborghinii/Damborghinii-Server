package org.dongguk.dambo.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "investment_progresses")
public class InvestmentProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "progress", nullable = false, precision = 3, scale = 1)
    private BigDecimal progress;

    @Column(name = "progress_amount", nullable = false)
    private Long progressAmount;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Contract contract;

    @Builder
    private InvestmentProgress(BigDecimal progress, Contract contract) {
        this.progress = progress;
        this.progressAmount = 0L;
        this.contract = contract;
    }

    public static InvestmentProgress create(BigDecimal progress, Contract contract) {
        return InvestmentProgress.builder()
                .progress(progress)
                .contract(contract)
                .build();
    }
}
