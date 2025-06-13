package org.dongguk.dambo.dto.usercontract.response;

import java.util.List;

public record RepaymentScheduleListResponse(
        List<RepaymentScheduleResponse> repaymentScheduleResponseList
) {
        public static RepaymentScheduleListResponse from(
                List<RepaymentScheduleResponse> repaymentScheduleResponseList
        ) {
            return new RepaymentScheduleListResponse(repaymentScheduleResponseList);
        }
}
