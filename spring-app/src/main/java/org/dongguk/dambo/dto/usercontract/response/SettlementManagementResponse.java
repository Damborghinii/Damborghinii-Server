package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SettlementManagementResponse(
        @JsonProperty("cash")
        Long cash,
        @JsonProperty("totalContracts")
        Integer totalContracts,
        @JsonProperty("totalAmount")
        Long totalAmount,
        @JsonProperty("repaymentSchedule")
        RepaymentScheduleListResponse repaymentScheduleListResponse
) {
        public static SettlementManagementResponse of(
                Long cash,
                Integer totalContracts,
                Long totalAmount,
                RepaymentScheduleListResponse repaymentScheduleListResponse
        ) {
                return new SettlementManagementResponse(
                        cash,
                        totalContracts,
                        totalAmount,
                        repaymentScheduleListResponse
                );
        }
}
