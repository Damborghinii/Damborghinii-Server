package org.dongguk.dambo.domain.exception.usercontract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserContractErrorCode implements ErrorCode {
    INVALID_CONTRACT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 계약 상태값 입니다."),
    INVALID_USER_CONTRACT_ROLE(HttpStatus.BAD_REQUEST, "유효하지 않은 역할값 입니다."),
    INVALID_REPAYMENT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 상환 상태값 입니다."),
    NOT_FOUND_USER_CONTRACT(HttpStatus.BAD_REQUEST, "상환 계약이 존재하지 않습니다."),
    INVALID_ROUND_OVERFLOW(HttpStatus.BAD_REQUEST, "현재 회차가 총 회차 수를 넘을 수 없습니다."),
    BORROWER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 채무자를 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
