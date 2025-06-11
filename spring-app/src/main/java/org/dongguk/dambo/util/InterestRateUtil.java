package org.dongguk.dambo.util;

import java.math.BigDecimal;

public class InterestRateUtil {
    public static BigDecimal getInterestRate(Long wonPrice) {
        if (wonPrice <= 10_000_000) {
            return BigDecimal.valueOf(0.12);
        } else if (wonPrice <= 30_000_000) {
            return BigDecimal.valueOf(0.11);
        } else if (wonPrice <= 50_000_000) {
            return BigDecimal.valueOf(0.10);
        } else if (wonPrice <= 100_000_000) {
            return BigDecimal.valueOf(0.09);
        } else {
            return BigDecimal.valueOf(0.08);
        }
    }
}
