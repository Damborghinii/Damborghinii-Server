package org.dongguk.dambo.dto.auth;

public record LoginRequest(
        String loginId,
        String password
) {
}
