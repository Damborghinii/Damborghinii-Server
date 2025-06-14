package org.dongguk.dambo.implement.repaymentschedule;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.domain.entity.RepaymentSchedule;
import org.dongguk.dambo.domain.type.ERepaymentStatus;
import org.dongguk.dambo.repository.repaymentschedule.RepaymentScheduleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RepaymentScheduleUpdater {
    @Transactional
    public void updateRepaymentScheduleOnRepayment(List<RepaymentSchedule> repaymentScheduleList) {
        repaymentScheduleList.forEach(
                repaymentSchedule -> repaymentSchedule.updateRepaymentStatusOnRepayment(ERepaymentStatus.REPAID)
        );
    }

    public void updateRepaymentSchedule(RepaymentSchedule repaymentSchedule) {
        repaymentSchedule.updateRepaymentStatusOnRepayment(ERepaymentStatus.REPAID);
    }
}
