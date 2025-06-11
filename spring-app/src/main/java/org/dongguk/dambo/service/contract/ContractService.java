package org.dongguk.dambo.service.contract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.*;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.contract.request.LoanCreateRequest;
import org.dongguk.dambo.implement.contract.ContractReader;
import org.dongguk.dambo.implement.contract.ContractUpdater;
import org.dongguk.dambo.implement.user.UserReader;
import org.dongguk.dambo.implement.investmentprogress.InvestmentProgressSaver;
import org.dongguk.dambo.implement.usercontract.UserContractSaver;
import org.dongguk.dambo.util.InterestRateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContractService {
    private final UserReader userReader;
    private final UserContractSaver userContractSaver;
    private final ContractReader contractReader;
    private final ContractUpdater contractUpdater;
    private final InvestmentProgressSaver investmentProgressSaver;

    @Transactional
    public Void createLoan(
            Long userId,
            Long contractId,
            LoanCreateRequest loanCreateRequest
    ) {
        User currentUser = userReader.findById(userId);
        Contract currentContract = contractReader.findRegisteredContractById(contractId);
        Long loanAmount = loanCreateRequest.loanAmount();
        Integer repaymentCount = loanCreateRequest.repaymentCount();

        BigDecimal interestRate = InterestRateUtil.getInterestRate(
                currentContract.getMusicCopyright().getWonPrice()
        );
        contractUpdater.updateContract(currentContract, loanAmount, repaymentCount, interestRate);

        UserContract currentUserContract = UserContract.create(
                EContractRole.BORROWER,
                null,
                null,
                repaymentCount,
                EContractStatus.INVESTING,
                currentUser,
                currentContract
        );
        userContractSaver.saveUserContract(currentUserContract);

        InvestmentProgress investmentProgress = InvestmentProgress.create(
                BigDecimal.valueOf(0),
                currentContract
        );
        investmentProgressSaver.saveInvestmentProgress(investmentProgress);

        return null;
    }
}
