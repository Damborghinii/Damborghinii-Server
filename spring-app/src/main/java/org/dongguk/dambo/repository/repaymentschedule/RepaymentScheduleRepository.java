package org.dongguk.dambo.repository.repaymentschedule;

import org.dongguk.dambo.domain.entity.RepaymentSchedule;
import org.dongguk.dambo.domain.entity.UserContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentSchedule, Long> {
    @Query("SELECT rs FROM RepaymentSchedule rs " +
            "WHERE rs.userContract IN :userContractList AND rs.round = :round")
    List<RepaymentSchedule> findRepaymentScheduleByRoundAndUserContract(
            List<UserContract> userContractList,
            Integer round
    );
}
