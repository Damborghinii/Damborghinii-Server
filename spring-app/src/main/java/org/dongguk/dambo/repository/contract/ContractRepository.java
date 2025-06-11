package org.dongguk.dambo.repository.contract;

import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByIdAndStatus(Long id, EContractStatus status);
}
