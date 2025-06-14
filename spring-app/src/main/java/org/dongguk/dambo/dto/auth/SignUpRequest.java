package org.dongguk.dambo.dto.auth;

public record SignUpRequest(
        String loginId,
        String password,
        String birth,
        String phoneNumber,
        String job,
        String walletAddr,
        String name
) {
}
