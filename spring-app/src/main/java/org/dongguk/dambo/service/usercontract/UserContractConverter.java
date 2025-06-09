package org.dongguk.dambo.service.usercontract;

import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.exception.usercontract.UserContractErrorCode;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.dongguk.dambo.domain.type.EContractRole.BORROWER;
import static org.dongguk.dambo.domain.type.EContractRole.LENDER;
import static org.dongguk.dambo.domain.type.EContractStatus.INVESTING;
import static org.dongguk.dambo.domain.type.EContractStatus.MATCHED;

@Component
public class UserContractConverter {
    public List<EContractStatus> convertToEContractStatus(String status) {
        return switch (status) {
            case "ALL" -> List.of(INVESTING, MATCHED);
            case "INVESTING" -> List.of(INVESTING);
            case "MATCHED" -> List.of(MATCHED);
            default -> throw new CustomException(UserContractErrorCode.INVALID_CONTRACT_STATUS);
        };
    }

    public EContractRole convertToContractRole(String role) {
        return switch (role) {
            case "BORROWER" -> BORROWER;
            case "LENDER" -> LENDER;
            default -> throw new CustomException(UserContractErrorCode.INVALID_USER_CONTRACT_ROLE);
        };
    }
}
