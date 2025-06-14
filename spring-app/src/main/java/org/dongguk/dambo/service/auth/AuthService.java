package org.dongguk.dambo.service.auth;

import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.domain.entity.User;
import org.dongguk.dambo.domain.exception.user.UserErrorCode;
import org.dongguk.dambo.domain.type.EJob;
import org.dongguk.dambo.dto.auth.SignUpRequest;
import org.dongguk.dambo.dto.jwt.JwtTokensDto;
import org.dongguk.dambo.repository.user.UserRepository;
import org.dongguk.dambo.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public JwtTokensDto signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByLoginId(signUpRequest.loginId())) {
            throw CustomException.type(UserErrorCode.USER_CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(signUpRequest.password());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        User user = userRepository.save(
                User.create(
                        signUpRequest.loginId(),
                        encodedPassword,
                        signUpRequest.name(),
                        LocalDate.parse(signUpRequest.birth(), formatter),
                        EJob.valueOf(signUpRequest.job()),
                        signUpRequest.phoneNumber(),
                        signUpRequest.walletAddr()
                )
        );

        return jwtUtil.generateTokens(user.getId());
    }

    @Transactional
    public JwtTokensDto login(
            String loginId,
            String password
    ) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> CustomException.type(UserErrorCode.NOT_FOUND_USER));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw CustomException.type(UserErrorCode.NOT_MATCH_PASSWORD);
        }

        return jwtUtil.generateTokens(user.getId());
    }

    @Transactional(readOnly = true)
    public void checkDuplicateId(String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw CustomException.type(UserErrorCode.USER_CONFLICT);
        }
    }
}

