package org.dongguk.dambo.controller.invest;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.contract.response.ContractDetailResponse;
import org.dongguk.dambo.dto.contract.response.ContractListResponse;
import org.dongguk.dambo.service.invest.InvestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InvestController {
    private final InvestService investService;

    @GetMapping("/contracts")
    public BaseResponse<ContractListResponse> getAllContracts() {
        return BaseResponse.success(investService.getAllContracts());
    }

    @GetMapping("/contracts/{contractId}")
    public BaseResponse<ContractDetailResponse> getContractDetail(
            @UserId Long userId,
            @PathVariable Long contractId
    ) {
        return BaseResponse.success(investService.getContractDetail(userId, contractId));
    }

    @GetMapping("/contracts/{contractId}/invest")
    public BaseResponse<?> getInvestmentInputMeta(
            @UserId Long userId,
            @PathVariable Long contractId
    ) {
        return BaseResponse.success(investService.getInvestmentInputMeta(userId, contractId));
    }
}
