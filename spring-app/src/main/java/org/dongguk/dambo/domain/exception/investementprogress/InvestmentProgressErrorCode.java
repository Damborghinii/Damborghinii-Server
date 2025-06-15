package org.dongguk.dambo.domain.exception.investementprogress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum InvestmentProgressErrorCode implements ErrorCode {
    NOT_FOUND_INVESTMENT_PROGRESS(HttpStatus.NOT_FOUND, "존재하지 않는 진행 상태입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
