package org.dongguk.dambo.dto.contract.response;

import lombok.Builder;

@Builder
public record ContractDetailResponse(
        ContractInfo contract,
        UserInfo user,
        CopyrightInfo copyright,
        ProgressInfo progress
) {

    @Builder
    public record ContractInfo(
            String loanAmount,
            String monthlyInterest,
            String loanType,
            String interestRate,
            String paymentAmount,
            String overdueRate,
            String repaymentPeriod,
            String repaymentCount
    ) { }

    @Builder
    public record UserInfo(
            String name,
            String job
    ) { }

    @Builder
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
    ) { }

    @Builder
    public record ProgressInfo(
            String currentProgress,
            String remainingInvestingMoney
    ) { }
}
