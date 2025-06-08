package org.dongguk.dambo.dto.usercontract.response;

import java.util.List;

public record ActiveContractListResponse(
        List<ActiveContractResponse> activeContractResponseList
) {
    public static ActiveContractListResponse from(List<ActiveContractResponse> activeContractResponseList) {
        return new ActiveContractListResponse(activeContractResponseList);
    }
}
