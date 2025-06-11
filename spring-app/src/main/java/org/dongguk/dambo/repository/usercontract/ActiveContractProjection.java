package org.dongguk.dambo.repository.usercontract;

import java.math.BigDecimal;

public interface ActiveContractProjection {
    Long getLoanAmount();
    BigDecimal getInterestRate();
    String getOwner();
    BigDecimal getEthPrice();
    String getStatus();
    BigDecimal getProgress();
    Long getInvestment();
    BigDecimal getStake();
}
