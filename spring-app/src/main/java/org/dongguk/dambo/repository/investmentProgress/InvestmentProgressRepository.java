package org.dongguk.dambo.repository.investmentProgress;

import org.dongguk.dambo.domain.entity.InvestmentProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestmentProgressRepository extends JpaRepository<InvestmentProgress, Long> {
    Optional<InvestmentProgress> findByContractId(Long contractId);
}
