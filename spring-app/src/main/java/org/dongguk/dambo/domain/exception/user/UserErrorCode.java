package org.dongguk.dambo.domain.exception.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않은 사용자입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
