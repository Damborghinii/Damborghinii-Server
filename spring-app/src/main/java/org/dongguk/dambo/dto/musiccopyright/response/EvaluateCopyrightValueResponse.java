package org.dongguk.dambo.dto.musiccopyright.response;

import lombok.Builder;

@Builder
public record EvaluateCopyrightValueResponse(
        String ethPrice,
        String wonPrice
) {
}
