package org.dongguk.dambo.controller.loan;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.annotation.UserId;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.dto.loan.LoanEvaluationCheckResponse;
import org.dongguk.dambo.dto.loan.LoanEvaluationResponse;
import org.dongguk.dambo.dto.loan.LoanEvaluationResponseV2;
import org.dongguk.dambo.service.loan.LoanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contracts")
public class LoanController {
    private final LoanService loanService;

    @GetMapping("/{contractId}/loans")
    public BaseResponse<LoanEvaluationResponse> evaluateLoan(
            @UserId Long userId,
            @PathVariable Long contractId
    ) {
        return BaseResponse.success(loanService.evaluateLoan(userId, contractId));
    }

    @GetMapping("/{contractId}/loans2")
    public BaseResponse<LoanEvaluationResponseV2> evaluateLoanV2(
            @UserId Long userId,
            @PathVariable Long contractId
    ) {
        return BaseResponse.success(loanService.evaluateLoanV2(userId, contractId));
    }

    @GetMapping("/{contractId}/loans/check")
    public BaseResponse<LoanEvaluationCheckResponse> evaluateLoanCheck(
            @UserId Long userId,
            @PathVariable Long contractId,
            @RequestParam Long amount,
            @RequestParam Long count
    ) {
        return BaseResponse.success(loanService.evaluateLoanCheck(userId, contractId, amount, count));
    }
}
