package org.dongguk.dambo.repository.contract;

import org.dongguk.dambo.domain.type.EContractStatus;

import java.math.BigDecimal;

public interface CopyrightSummaryProjection {
    Long getId();
    Long getContractId();
    String getImageUrl();
    String getTitle();
    BigDecimal getEthPrice();
    EContractStatus getStatus();
}
