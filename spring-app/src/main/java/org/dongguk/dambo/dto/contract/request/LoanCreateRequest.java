package org.dongguk.dambo.dto.contract.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoanCreateRequest(
        @JsonProperty("loan_amount")
        Long loanAmount,
        @JsonProperty("repayment_count")
        Integer repaymentCount
) {
}
