package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.exception.usercontract.UserContractErrorCode;

@RequiredArgsConstructor
@Getter
public enum ERepaymentStatus {
    UPCOMING("상환예정"),
    OVERDUE("미상환"),
    REPAID("상환완료");

    private final String label;

    public static ERepaymentStatus convertToERepaymentStatus(String status) {
        return switch (status) {
            case "UPCOMING" -> UPCOMING;
            case "OVERDUE" -> OVERDUE;
            case "REPAID" -> REPAID;
            default -> throw new CustomException(UserContractErrorCode.INVALID_CONTRACT_STATUS);
        };
    }
}
