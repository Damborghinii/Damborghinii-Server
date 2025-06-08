package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EContractRole {
    BORROWER("대출자"),
    LENDER("투자자");

    private final String label;
}
