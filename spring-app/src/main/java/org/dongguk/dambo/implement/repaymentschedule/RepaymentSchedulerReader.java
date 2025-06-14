package org.dongguk.dambo.implement.repaymentschedule;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.RepaymentSchedule;
import org.dongguk.dambo.domain.entity.UserContract;
import org.dongguk.dambo.domain.exception.repaymentschedule.RepaymentScheduleErrorCode;
import org.dongguk.dambo.repository.repaymentschedule.RepaymentScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RepaymentSchedulerReader {
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    public RepaymentSchedule findById(Long contractId) {
        return repaymentScheduleRepository.findById(contractId)
                .orElseThrow(() -> new CustomException(RepaymentScheduleErrorCode.NOT_FOUND_REPAYMENT_SCHEDULE));
    }

    public List<RepaymentSchedule> findByUserContractAndRound(List<UserContract> userContractList, Integer round) {
        return repaymentScheduleRepository.findRepaymentScheduleByRoundAndUserContract(
                userContractList, round
        );
    }
}
