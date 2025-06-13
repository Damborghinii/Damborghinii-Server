package org.dongguk.dambo.dto.contract.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoanCreateRequest(
        @JsonProperty("loanAmount")
        Long loanAmount,
        @JsonProperty("repaymentCount")
        Integer repaymentCount
) {
}
