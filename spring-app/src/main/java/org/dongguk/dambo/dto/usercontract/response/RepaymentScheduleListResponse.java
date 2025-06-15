package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RepaymentScheduleListResponse(
        @JsonProperty("repaymentScheduleList")
        List<RepaymentScheduleResponse> repaymentScheduleResponseList
) {
        public static RepaymentScheduleListResponse from(
                List<RepaymentScheduleResponse> repaymentScheduleResponseList
        ) {
            return new RepaymentScheduleListResponse(repaymentScheduleResponseList);
        }
}
