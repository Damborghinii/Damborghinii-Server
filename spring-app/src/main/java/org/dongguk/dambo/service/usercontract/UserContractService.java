package org.dongguk.dambo.service.usercontract;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.RepaymentSchedule;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.entity.UserContract;
import org.dongguk.dambo.domain.type.EContractRole;
import org.dongguk.dambo.domain.type.EContractStatus;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.dongguk.dambo.dto.usercontract.response.*;
import org.dongguk.dambo.implement.repaymentschedule.RepaymentScheduleUpdater;
import org.dongguk.dambo.implement.repaymentschedule.RepaymentSchedulerReader;
import org.dongguk.dambo.implement.user.UserReader;
import org.dongguk.dambo.implement.user.UserUpdater;
import org.dongguk.dambo.implement.usercontract.UserContractReader;
import org.dongguk.dambo.implement.usercontract.UserContractUpdater;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserContractService {
    private final UserContractReader userContractReader;
    private final UserReader userReader;
    private final UserUpdater userUpdater;
    private final RepaymentSchedulerReader repaymentSchedulerReader;
    private final RepaymentScheduleUpdater repaymentScheduleUpdater;
    private final UserContractUpdater userContractUpdater;

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

    @Transactional
    public Void updateRepaymentSchedule(Long userId, Long repaymentScheduleId) {
        User currentUser = userReader.findById(userId);
        RepaymentSchedule repaymentSchedule = repaymentSchedulerReader.findById(repaymentScheduleId);
        Integer currentRound = repaymentSchedule.getRound();
        List<UserContract> otherUserContractList = userContractReader.findUserContractByContractWithLender(repaymentSchedule.getUserContract().getContract());
        List<User> otherUserList = otherUserContractList
                .stream().map(UserContract::getUser).toList();
        List<Long> repaymentAmountList = otherUserContractList.stream()
                .map(uc -> uc.getStake().longValue() * repaymentSchedule.getRepaymentAmount() / 100)
                .toList();
        List<RepaymentSchedule> repaymentScheduleList = repaymentSchedulerReader.findByUserContractAndRound(
                otherUserContractList, currentRound
        );

        userUpdater.updateUserCashOnRepayment(currentUser, repaymentSchedule.getRepaymentAmount());
        userUpdater.updateOtherUserCashOnRepayment(otherUserList, repaymentAmountList);
        repaymentScheduleUpdater.updateRepaymentSchedule(repaymentSchedule);
        repaymentScheduleUpdater.updateRepaymentScheduleOnRepayment(repaymentScheduleList);
        userContractUpdater.updateRound(otherUserContractList);

        return null;
    }
}
