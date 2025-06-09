package org.dongguk.dambo.service.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractListResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserContractService {
    private final UserContractRetriever userContractRetriever;

    public ActiveContractListResponse getActiveContractsByUserIdAndStatuesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole role
    ) {
        List<ActiveContractResponse> activeContractResponseList
                = userContractRetriever.findActiveContractsByUserAndStatusesAndRole(userId, statuses, role);

        return ActiveContractListResponse.from(activeContractResponseList);
    }
}
