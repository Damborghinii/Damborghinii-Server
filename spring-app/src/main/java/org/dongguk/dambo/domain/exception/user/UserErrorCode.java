package org.dongguk.dambo.domain.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않은 사용자입니다."),
    USER_CONFLICT(HttpStatus.CONFLICT, "이미 등록된 유저입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    INSUFFICIENT_BALANCE(HttpStatus.BAD_REQUEST, "보유 금액이 부족합니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
