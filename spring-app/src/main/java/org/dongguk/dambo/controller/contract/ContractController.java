package org.dongguk.dambo.controller.contract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.contract.request.LoanCreateRequest;
import org.dongguk.dambo.service.contract.ContractService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ContractController {
    private final ContractService contractService;

    @PostMapping("contracts/{contractId}")
    public BaseResponse<Void> createContract(
            @RequestHeader("user_id") Long userId,
            @PathVariable Long contractId,
            @RequestBody LoanCreateRequest loanCreateRequest
    ) {
        return BaseResponse.success(contractService.createLoan(userId, contractId, loanCreateRequest));
    }
}
