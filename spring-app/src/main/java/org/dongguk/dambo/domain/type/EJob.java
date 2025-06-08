package org.dongguk.dambo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EJob {
    COMPANY("회사원"),
    SELF_EMPLOYED("자영업"),
    FREELANCER("프리랜서"),
    SOLDIER("군인"),
    PUBLIC_OFFICIAL("공무원"),
    HOMEMAKER("전업주부"),
    PROFESSIONAL("전문직"),
    STUDENT("학생"),
    UNEMPLOYED("무직"),
    OTHER("기타");

    private final String label;
}
