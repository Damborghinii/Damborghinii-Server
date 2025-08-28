package org.dongguk.dambo.dto.musiccopyright.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MyCopyrightResponse(
        Long copyrightId,
        Long contractId,
        String imageUrl,
        String title,
        String type,
        String ethPrice,
        String status,
        BigDecimal progress,
        Integer repaymentCount,
        Integer round

) {
}
