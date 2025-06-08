package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EContractStatus {
    REGISTERED("등록"),
    INVESTING("투자진행중"),
    MATCHED("계약중"),
    COMPLETED("계약완료");

    private final String label;
}
