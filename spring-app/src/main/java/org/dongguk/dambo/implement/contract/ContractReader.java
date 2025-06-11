package org.dongguk.dambo.implement.contract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.Contract;
import org.dongguk.dambo.domain.exception.contract.ContractErrorCode;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.repository.contract.ContractRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractReader {
    private final ContractRepository contractRepository;

    public Contract findRegisteredContractById(Long id) {
        return contractRepository.findByIdAndStatus(id, EContractStatus.REGISTERED)
                .orElseThrow(() -> CustomException.type(ContractErrorCode.INVALID_CONTRACT));
    }
}
