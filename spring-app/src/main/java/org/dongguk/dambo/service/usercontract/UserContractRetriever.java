package org.dongguk.dambo.service.usercontract;

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
public class UserContractRetriever {
    private final UserContractRepository userContractRepository;

    public List<ActiveContractResponse> findActiveContractsByUserAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole role
    ) {
        return userContractRepository.findActiveContractsByUserIdAndStatusesAndRole(userId, statuses, role)
                .stream()
                .map(ActiveContractResponse::from)
                .collect(Collectors.toList());
    }
}
