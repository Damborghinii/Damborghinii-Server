package org.dongguk.dambo.repository.contract;

import java.math.BigDecimal;

public interface ContractProjection {
    Long getContractId();
    Long getLoanAmount();
    BigDecimal getInterestRate();
    String getCopyrightName();
    BigDecimal getCopyrightEthPrice();
    BigDecimal getProgress();
}