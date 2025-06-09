package org.dongguk.dambo.dto.usercontract.response;

public record ActiveContractCountResponse(
        Long count
) {
    public static ActiveContractCountResponse of(Long count) {
        return new ActiveContractCountResponse(count);
    }
}
