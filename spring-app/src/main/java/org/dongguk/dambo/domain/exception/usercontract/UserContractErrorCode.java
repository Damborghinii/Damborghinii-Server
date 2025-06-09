package org.dongguk.dambo.domain.exception.usercontract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserContractErrorCode implements ErrorCode {
    INVALID_CONTRACT_STATUS(HttpStatus.BAD_REQUEST, "유효하지 않은 계약 상태값 입니다."),
    INVALID_USER_CONTRACT_ROLE(HttpStatus.BAD_REQUEST, "유효하지 않은 역할값 입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
