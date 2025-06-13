package org.dongguk.dambo.service.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.dongguk.dambo.dto.usercontract.response.*;
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
        EContractRole contractRole = EContractRole.convertToContractRole(role);

        List<ActiveContractResponse> activeContractResponseList
                = userContractReader.findActiveContractsByUserAndStatusesAndRole(userId, statuses, contractRole);

        return ActiveContractListResponse.from(activeContractResponseList);
    }

    public ActiveContractCountResponse getActiveContractsCountByUserIdAndStatuesAndRole(
            Long userId,
            String status,
            String role
    ) {
        List<EContractStatus> statuses = EContractStatus.convertToEContractStatus(status);
        EContractRole contractRole = EContractRole.convertToContractRole(role);

        return ActiveContractCountResponse.of(
                userContractReader.findActiveContractsCountByUserAndStatusesAndRole(userId, statuses, contractRole)
        );
    }

    public SettlementManagementResponse getSettlementManagement(
            Long userId,
            String status,
            String role
    ) {
        ERepaymentStatus repaymentStatus = ERepaymentStatus.convertToERepaymentStatus(status);
        EContractRole contractRole = EContractRole.convertToContractRole(role);

        Long cash = userContractReader.findCashByUserId(userId);
        Integer totalContracts = userContractReader.findTotalContractsByUserIdAndRole(
                userId,
                contractRole
        );
        Long totalAmount = userContractReader.findTotalAmountByUserIdAndStatusAndRole(
                userId,
                repaymentStatus,
                contractRole
        );

        List<RepaymentScheduleResponse> repaymentScheduleResponseList
                = userContractReader.findRepaymentScheduleByUserIdAndStatusAndRole(
                        userId,
                        repaymentStatus,
                        contractRole
        );

        return SettlementManagementResponse.of(
                cash,
                totalContracts,
                totalAmount,
                RepaymentScheduleListResponse.from(repaymentScheduleResponseList)
        );

    }
}
