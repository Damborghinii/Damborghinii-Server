package org.dongguk.dambo.implement.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.dongguk.dambo.dto.usercontract.response.ActiveContractResponse;
import org.dongguk.dambo.dto.usercontract.response.RepaymentScheduleResponse;
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
            EContractRole role
    ) {
        return userContractRepository.findActiveContractsByUserIdAndStatusesAndRole(userId, statuses, role)
                .stream()
                .map(ActiveContractResponse::from)
                .collect(Collectors.toList());
    }

    public Long findActiveContractsCountByUserAndStatusesAndRole(
            Long userId,
            List<EContractStatus> statuses,
            EContractRole role
    ) {
        return userContractRepository.findActiveContractsCountByUserIdAndStatusesAndRole(userId, statuses, role);
    }

    public Long findCashByUserId(Long userId) {
        return userContractRepository.findCashByUserId(userId);
    }

    public Integer findTotalContractsByUserIdAndRole(
            Long userId,
            EContractRole role
    ) {
        return userContractRepository.findTotalContractsByUserIdAndRole(userId, role);
    }

    public Long findTotalAmountByUserIdAndStatusAndRole(
            Long userId,
            ERepaymentStatus status,
            EContractRole role
    ) {
        return userContractRepository.findTotalAmountByUserIdAndStatusAndRole(
                userId,
                status,
                role
        );
    }

    public List<RepaymentScheduleResponse> findRepaymentScheduleByUserIdAndStatusAndRole(
            Long userId,
            ERepaymentStatus status,
            EContractRole role
    ) {
        return userContractRepository.findRepaymentScheduleByUserIdAndStatusAndRole(userId, status, role)
                .stream()
                .map(RepaymentScheduleResponse::from)
                .collect(Collectors.toList());
    }
}
