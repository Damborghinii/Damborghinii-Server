package org.dongguk.dambo.controller.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractListResponse;
import org.dongguk.dambo.service.usercontract.UserContractService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/me/contracts")
public class UserContractController {
    private final UserContractService userContractService;

    @GetMapping("/active")
    public BaseResponse<ActiveContractListResponse> getActiveContracts(
            @RequestHeader("user_id") Long userId,
            @RequestParam("status") List<String> statuses,
            @RequestParam EContractRole role
    ) {
        return BaseResponse.success(userContractService.getActiveContractsByUserIdAndStatuesAndRole(userId, statuses, role));
    }
}
