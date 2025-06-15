package org.dongguk.dambo.dto.usercontract.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dongguk.dambo.repository.usercontract.RepaymentScheduleProjection;

import java.math.BigDecimal;

public record RepaymentScheduleResponse(
        @JsonProperty("repaymentScheduleId")
        Long repaymentScheduleId,
        @JsonProperty("totalRepaymentAmount")
        Long totalRepaymentAmount,
        @JsonProperty("repaymentAmount")
        Long repaymentAmount,
        @JsonProperty("interestRate")
        BigDecimal interestRate,
        @JsonProperty("lateFee")
        Long lateFee,
        @JsonProperty("round")
        Integer round,
        @JsonProperty("repaymentDate")
        String repaymentDate,
        @JsonProperty("settlementDate")
        String settlementDate,
        @JsonProperty("relativeDays")
        String relativeDays,
        @JsonProperty("nftName")
        String nftName,
        @JsonProperty("stake")
        Long stake,
        @JsonProperty("ethPrice")
        BigDecimal ethPrice
) {
        public static RepaymentScheduleResponse from(RepaymentScheduleProjection repaymentScheduleProjection) {
                return new RepaymentScheduleResponse(
                        repaymentScheduleProjection.getRepaymentScheduleId(),
                        repaymentScheduleProjection.getTotalRepaymentAmount(),
                        repaymentScheduleProjection.getRepaymentAmount(),
                        repaymentScheduleProjection.getInterestRate(),
                        repaymentScheduleProjection.getLateFee(),
                        repaymentScheduleProjection.getRound(),
                        repaymentScheduleProjection.getRepaymentDate().toString(),
                        repaymentScheduleProjection.getSettlementDate() != null ?
                                repaymentScheduleProjection.getSettlementDate().toString() : null,
                        repaymentScheduleProjection.getRelativeDays() == null ? null :
                                repaymentScheduleProjection.getRelativeDays() >= 0 ?
                                        repaymentScheduleProjection.getRelativeDays() + "일 남았어요" :
                                        (-repaymentScheduleProjection.getRelativeDays()) + "일 초과됐어요",
                        repaymentScheduleProjection.getNftName(),
                        repaymentScheduleProjection.getStake(),
                        repaymentScheduleProjection.getEthPrice()
                );
        }
}
