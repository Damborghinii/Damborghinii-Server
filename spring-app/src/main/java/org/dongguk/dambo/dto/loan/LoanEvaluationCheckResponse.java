package org.dongguk.dambo.dto.loan;


public record LoanEvaluationCheckResponse(
        LoanCondition loanCondition,
        CopyrightInfo copyright
) {
    public record LoanCondition(
            String loanType,
            String loanAmount,
            String interestRate,
            String monthlyInterest,
            String totalPayment,
            String overdueRate,
            String loanPeriod,
            String repaymentCount
    ) { }

    /** ② 저작권 정보 블록 */
    public record CopyrightInfo(
            String imageUrl,
            String title,
            String ethPrice,
            String wonPrice
    ) { }
}