package org.dongguk.dambo.domain.exception.repaymentschedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RepaymentScheduleErrorCode implements ErrorCode {
    NOT_FOUND_REPAYMENT_SCHEDULE(HttpStatus.NOT_FOUND, "존재하지 않는 상환 일정입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
