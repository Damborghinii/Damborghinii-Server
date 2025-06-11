package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.exception.usercontract.UserContractErrorCode;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum EContractStatus {
    REGISTERED("등록"),
    INVESTING("투자진행중"),
    MATCHED("계약중"),
    COMPLETED("계약완료");

    private final String label;

    public static List<EContractStatus> convertToEContractStatus(String status) {
        return switch (status) {
            case "ALL" -> List.of(INVESTING, MATCHED);
            case "INVESTING" -> List.of(INVESTING);
            case "MATCHED" -> List.of(MATCHED);
            default -> throw new CustomException(UserContractErrorCode.INVALID_CONTRACT_STATUS);
        };
    }
}
