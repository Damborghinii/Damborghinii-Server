package org.dongguk.dambo.controller.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractCountResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractListResponse;
import org.dongguk.dambo.service.usercontract.UserContractService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/me/contracts")
public class UserContractController {
    private final UserContractService userContractService;

    @GetMapping("/active")
    public BaseResponse<ActiveContractListResponse> getActiveContracts(
            @RequestHeader("user_id") Long userId,
            @RequestParam("status") String status,
            @RequestParam String role
    ) {
        return BaseResponse.success(userContractService.getActiveContractsByUserIdAndStatuesAndRole(userId, status, role));
    }

    @GetMapping("/active/count")
    public BaseResponse<ActiveContractCountResponse> getActiveContractsCount(
            @RequestHeader("user_id") Long userId,
            @RequestParam("status") String status,
            @RequestParam String role
    ) {
        return BaseResponse.success(userContractService.getActiveContractsCountByUserIdAndStatuesAndRole(userId, status, role));
    }
}
