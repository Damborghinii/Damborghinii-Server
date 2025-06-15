package org.dongguk.dambo.repository.usercontract;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RepaymentScheduleProjection {
    Long getRepaymentScheduleId();
    Long getTotalRepaymentAmount();
    Long getRepaymentAmount();
    BigDecimal getInterestRate();
    Long getLateFee();
    Integer getRound();
    LocalDate getRepaymentDate();
    LocalDate getSettlementDate();
    Integer getRelativeDays();
    String getNftImageUrl();
    String getNftName();
    Long getStake();
    BigDecimal getEthPrice();
}
