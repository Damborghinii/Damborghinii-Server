package org.dongguk.dambo.dto.loan;

import java.math.BigDecimal;

public record LoanEvaluationResponseV2(
        LoanCondition loanCondition,
        String minimumLoanAmount,
        String maximumLoanAmount,
        BigDecimal interestCalculationRatio
) {
    public record LoanCondition(
            String loanType,
            String loanPeriod,
            String loanAmount,
            String interestRate,
            String overdueRate
    ) {}
}

