package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dongguk.dambo.domain.type.EContractStatus;

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
        public ActiveContractResponse(
                Long loanAmount,
                BigDecimal interestRate,
                String owner,
                BigDecimal price,
                EContractStatus status,
                BigDecimal progress,
                Long investment,
                BigDecimal stake
        ) {
                this(loanAmount, interestRate, owner, price, status.getLabel(), progress, investment, stake);
        }
}
