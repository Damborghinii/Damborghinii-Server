package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dongguk.dambo.repository.usercontract.ActiveContractProjection;

import java.math.BigDecimal;

public record ActiveContractResponse(
        @JsonProperty("loan_amount")
        Long loanAmount,
        @JsonProperty("interest_rate")
        BigDecimal interestRate,
        @JsonProperty("owner")
        String owner,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("status")
        String status,
        @JsonProperty("progress")
        BigDecimal progress,
        @JsonProperty("investment")
        Long investment,
        @JsonProperty("stake")
        BigDecimal stake
) {
        public static ActiveContractResponse from(ActiveContractProjection activeContractProjection){
                return new ActiveContractResponse(
                        activeContractProjection.getLoanAmount(),
                        activeContractProjection.getInterestRate(),
                        activeContractProjection.getOwner(),
                        activeContractProjection.getPrice(),
                        activeContractProjection.getStatus(),
                        activeContractProjection.getProgress(),
                        activeContractProjection.getInvestment(),
                        activeContractProjection.getStake()
                );
        }
}
