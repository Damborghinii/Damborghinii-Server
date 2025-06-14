package org.dongguk.dambo.dto.jwt;

import lombok.Builder;

@Builder
public record JwtTokensDto(
        String accessToken,
        String refreshToken
) {
}