package org.dongguk.dambo.dto.contract.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record ContractListResponse(
        List<ContractItem> contracts
) {

    @Builder
    public record ContractItem(
            Long contractId,
            String loanAmount,
            String interestRate,
            CopyrightInfo copyright,
            String progress,
            String expirationTime
    ) { }

    @Builder
    public record CopyrightInfo(
            String imageUrl,
            String name,
            String ethPrice
    ) { }
}