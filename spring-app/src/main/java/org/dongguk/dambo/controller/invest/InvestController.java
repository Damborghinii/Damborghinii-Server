package org.dongguk.dambo.controller.invest;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.domain.type.ESearchFilter;
import org.dongguk.dambo.dto.contract.response.ContractDetailResponse;
import org.dongguk.dambo.dto.contract.response.ContractListResponse;
import org.dongguk.dambo.dto.invest.request.InvestmentRequest;
import org.dongguk.dambo.dto.invest.response.InvestmentInputMetaResponse;
import org.dongguk.dambo.service.invest.InvestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contracts")
public class InvestController {
    private final InvestService investService;

    @GetMapping("")
    public BaseResponse<ContractListResponse> getAllContracts(
            @RequestParam ESearchFilter searchFilter
    ) {
        return BaseResponse.success(investService.getAllContracts(searchFilter));
    }

    @GetMapping("/{contractId}")
    public BaseResponse<ContractDetailResponse> getContractDetail(
            @UserId Long userId,
            @PathVariable Long contractId
    ) {
        return BaseResponse.success(investService.getContractDetail(userId, contractId));
    }

    @GetMapping("/{contractId}/invest")
    public BaseResponse<InvestmentInputMetaResponse> getInvestmentInputMeta(
            @UserId Long userId,
            @PathVariable Long contractId
    ) {
        return BaseResponse.success(investService.getInvestmentInputMeta(userId, contractId));
    }

    @PostMapping("/{contractId}/invest")
    public BaseResponse<Void> investContract(
            @UserId Long userId,
            @PathVariable Long contractId,
            @RequestBody InvestmentRequest investmentRequest
    ) {
        return BaseResponse.success(investService.investContract(userId, contractId, investmentRequest));
    }
}
