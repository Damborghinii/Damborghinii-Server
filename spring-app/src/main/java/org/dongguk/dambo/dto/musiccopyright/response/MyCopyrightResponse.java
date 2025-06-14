package org.dongguk.dambo.dto.musiccopyright.response;

import lombok.Builder;

@Builder
public record MyCopyrightResponse(
        Long copyrightId,
        String imageUrl,
        String title,
        String type,
        String ethPrice,
        String status
) {
}
