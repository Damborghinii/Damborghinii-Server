package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dongguk.dambo.repository.usercontract.ActiveContractProjection;

import java.math.BigDecimal;

public record ActiveContractResponse(
        @JsonProperty("loanAmount")
        Long loanAmount,
        @JsonProperty("interestRate")
        BigDecimal interestRate,
        @JsonProperty("owner")
        String owner,
        @JsonProperty("ethPrice")
        BigDecimal ehtPrice,
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
                        activeContractProjection.getEthPrice(),
                        activeContractProjection.getStatus(),
                        activeContractProjection.getProgress(),
                        activeContractProjection.getInvestment(),
                        activeContractProjection.getStake()
                );
        }
}
