package org.dongguk.dambo.implement.contract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.exception.contract.ContractErrorCode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ContractUpdater {
    @Transactional
    public void updateContract(
            Contract contract,
            Long loanAmount,
            Integer repaymentCount
    ) {
        if(loanAmount <= 0) {
            throw new CustomException(ContractErrorCode.INVALID_LOAN_AMOUNT);
        }
        if(repaymentCount <= 0) {
            throw new CustomException(ContractErrorCode.INVALID_REPAYMENT_COUNT);
        }

        contract.updateContractOnLoan(loanAmount, repaymentCount);
    }
}
