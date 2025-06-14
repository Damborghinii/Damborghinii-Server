package org.dongguk.dambo.domain.exception.musiccopyright;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MusicCopyrightErrorCode implements ErrorCode {
    NOT_FOUND_MUSIC_COPYRIGHT(HttpStatus.NOT_FOUND, "존재하지 않는 저작권입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
