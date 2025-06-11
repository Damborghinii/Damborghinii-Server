package org.dongguk.dambo.implement.investmentprogress;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.InvestmentProgress;
import org.dongguk.dambo.repository.investmentProgress.InvestmentProgressRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InvestmentProgressSaver {
    private final InvestmentProgressRepository investmentProgressRepository;

    @Transactional
    public InvestmentProgress saveInvestmentProgress(InvestmentProgress investmentProgress) {
        return investmentProgressRepository.save(investmentProgress);
    }
}
