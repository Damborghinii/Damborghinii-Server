package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.exception.usercontract.UserContractErrorCode;

@RequiredArgsConstructor
@Getter
public enum EContractRole {
    BORROWER("대출자"),
    LENDER("투자자");

    private final String label;

    public static EContractRole convertToContractRole(String role) {
        return switch (role) {
            case "BORROWER" -> BORROWER;
            case "LENDER" -> LENDER;
            default -> throw new CustomException(UserContractErrorCode.INVALID_USER_CONTRACT_ROLE);
        };
    }
}
