package org.dongguk.dambo.dto.loan;

public record LoanEvaluationConclusionResponse(
        CopyrightInfo copyright,
        LoanCondition loanCondition,
        String creationDate
) {
    public record CopyrightInfo(
            String title,
            String singers,
            String isRegistered,
            String wonPrice,
            String streamingUrls
    ) {}

    public record LoanCondition(
            String loanType,
            String loanAmount,
            String loanPeriod,
            String interestRate,
            String monthlyInterest,
            String overdueRate
    ) {}
}