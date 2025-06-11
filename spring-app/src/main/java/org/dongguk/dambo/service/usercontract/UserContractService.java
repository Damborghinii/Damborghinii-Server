package org.dongguk.dambo.service.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractCountResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractListResponse;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse;
import org.dongguk.dambo.implement.usercontract.UserContractReader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserContractService {
    private final UserContractReader userContractReader;

    public ActiveContractListResponse getActiveContractsByUserIdAndStatuesAndRole(
            Long userId,
            String status,
            String role
    ) {
        List<EContractStatus> statuses = EContractStatus.convertToEContractStatus(status);
        EContractRole eRole = EContractRole.convertToContractRole(role);

        List<ActiveContractResponse> activeContractResponseList
                = userContractReader.findActiveContractsByUserAndStatusesAndRole(userId, statuses, eRole);

        return ActiveContractListResponse.from(activeContractResponseList);
    }

    public ActiveContractCountResponse getActiveContractsCountByUserIdAndStatuesAndRole(
            Long userId,
            String status,
            String role
    ) {
        List<EContractStatus> statuses = EContractStatus.convertToEContractStatus(status);
        EContractRole eRole = EContractRole.convertToContractRole(role);

        return ActiveContractCountResponse.of(
                userContractReader.findActiveContractsCountByUserAndStatusesAndRole(userId, statuses, eRole)
        );
    }
}
