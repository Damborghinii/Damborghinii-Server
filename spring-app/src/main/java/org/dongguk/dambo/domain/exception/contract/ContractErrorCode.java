package org.dongguk.dambo.domain.exception.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContractErrorCode implements ErrorCode {
    INVALID_CONTRACT(HttpStatus.BAD_REQUEST, "대출 신청할 수 없는 계약입니다."),
    INVALID_LOAN_AMOUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 대출 금액입니다."),
    INVALID_REPAYMENT_COUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 대출 상환 횟수입니다."),
    NOT_FOUND_CONTRACT(HttpStatus.NOT_FOUND, "존재하지 않는 계약입니다."),
    INVALID_CONTRACT_STATUS(HttpStatus.BAD_REQUEST, "대출 신청할 수 없는 상태의 계약입니다."),
    OVER_INVESTMENT_LIMIT(HttpStatus.BAD_REQUEST, "투자 가능 금액을 넘어섰습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
