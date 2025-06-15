package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SettlementManagementResponse(
        @JsonProperty("cash")
        Long cash,
        @JsonProperty("totalContracts")
        Integer totalContracts,
        @JsonProperty("totalAmount")
        Long totalAmount,
        @JsonProperty("repaymentSchedules")
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
                        totalContracts == null ? 0 : totalContracts,
                        totalAmount == null ? 0 : totalAmount,
                        repaymentScheduleListResponse
                );
        }
}
