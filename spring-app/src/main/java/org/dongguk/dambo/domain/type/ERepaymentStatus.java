package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ERepaymentStatus {
    ON_TIME("정상 상환"),
    LATE("연체"),
    UNPAID("미납");

    private final String label;
}
