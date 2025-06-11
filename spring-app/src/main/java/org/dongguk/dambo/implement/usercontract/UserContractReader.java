package org.dongguk.dambo.implement.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse;
import org.dongguk.dambo.repository.usercontract.UserContractRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserContractReader {
    private final UserContractRepository userContractRepository;

    public List<ActiveContractResponse> findActiveContractsByUserAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole eRole
    ) {
        return userContractRepository.findActiveContractsByUserIdAndStatusesAndRole(userId, statuses, eRole)
                .stream()
                .map(ActiveContractResponse::from)
                .collect(Collectors.toList());
    }

    public Long findActiveContractsCountByUserAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole eRole
    ) {
        return userContractRepository.findActiveContractsCountByUserIdAndStatusesAndRole(userId, statuses, eRole);
    }
}
