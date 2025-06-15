package org.dongguk.dambo.dto.loan;

import java.math.BigDecimal;

public record LoanEvaluationResponse(
        CopyrightInfo copyright,
        LoanCondition loanCondition,
        String minimumLoanAmount,
        String maximumLoanAmount,
        BigDecimal interestCalculationRatio
) {
    public record CopyrightInfo(
            String imageUrl,
            String title,
            String type,
            String ethPrice,
            String wonPrice,
            String singers,
            String composers,
            String lyricists,
            String streamingUrls,
            String isRegistered,
            String registrationDoc
    ) {}

    public record LoanCondition(
            String loanType,
            String loanPeriod,
            String loanAmount,
            String interestRate,
            String overdueRate
    ) {}
}

