package org.dongguk.dambo.controller.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractCountResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractListResponse;
import org.dongguk.dambo.dto.usercontract.response.SettlementManagementResponse;
import org.dongguk.dambo.service.usercontract.UserContractService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/me/contracts")
public class UserContractController {
    private final UserContractService userContractService;

    @GetMapping("/active")
    public BaseResponse<ActiveContractListResponse> getActiveContracts(
            @UserId Long userId,
            @RequestParam String status,
            @RequestParam String role
    ) {
        return BaseResponse.success(userContractService.getActiveContractsByUserIdAndStatuesAndRole(userId, status, role));
    }

    @GetMapping("/active/count")
    public BaseResponse<ActiveContractCountResponse> getActiveContractsCount(
            @UserId Long userId,
            @RequestParam String status,
            @RequestParam String role
    ) {
        return BaseResponse.success(userContractService.getActiveContractsCountByUserIdAndStatuesAndRole(userId, status, role));
    }

    @GetMapping("/confirmed")
    public BaseResponse<SettlementManagementResponse> getSettlementManagement(
            @UserId Long userId,
            @RequestParam String status,
            @RequestParam String role
    ) {
        return BaseResponse.success(userContractService.getSettlementManagement(userId, status, role));
    }

    @PatchMapping("/{repaymentScheduleId}/repayment")
    public BaseResponse<Void> updateRepaymentSchedule(
            @UserId Long userId,
            @PathVariable Long repaymentScheduleId
    ) {
        return BaseResponse.success(userContractService.updateRepaymentSchedule(userId, repaymentScheduleId));
    }
}
