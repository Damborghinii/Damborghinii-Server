package org.dongguk.dambo.repository.contract;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ContractProjection {
    Long getContractId();
    Long getLoanAmount();
    BigDecimal getInterestRate();
    LocalDateTime getExpirationTime();
    String getCopyrightImageUrl();
    String getCopyrightName();
    BigDecimal getCopyrightEthPrice();
    BigDecimal getProgress();
}