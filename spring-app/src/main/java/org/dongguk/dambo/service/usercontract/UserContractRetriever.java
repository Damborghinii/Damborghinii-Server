package org.dongguk.dambo.service.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse;
import org.dongguk.dambo.repository.usercontract.UserContractRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserContractRetriever {
    private final UserContractRepository userContractRepository;

    public List<ActiveContractResponse> findActiveContractsByUserAndStatusesAndRole(
            Long userId,
            List<String> statuses,
            EContractRole role
    ) {
        return userContractRepository.findActiveContractsByUserIdAndStatusesAndRole(userId, statuses, role);
    }
}
