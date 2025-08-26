package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ESearchFilter {
    HIGH_RETURN("고수익"),
    LOW_RISK("비교적 안전한"),
    SHORT_TERM("단기 상환"),
    LONG_TERM("장기 상환"),
    ALL("전체");

    private final String label;
}
