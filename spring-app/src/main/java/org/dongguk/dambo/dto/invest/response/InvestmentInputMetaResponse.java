package org.dongguk.dambo.dto.invest.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InvestmentInputMetaResponse(
        String minimumLoanAmount,
        String maximumLoanAmount,
        BigDecimal shareCalculationRatio,
        BigDecimal interestCalculationRatio
) {
}
