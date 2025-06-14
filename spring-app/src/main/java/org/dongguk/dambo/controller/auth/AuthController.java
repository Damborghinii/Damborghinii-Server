package org.dongguk.dambo.controller.auth;


import lombok.RequiredArgsConstructor;
import org.dongguk.dambo.dto.auth.CheckDuplicateIdRequest;
import org.dongguk.dambo.dto.auth.LoginRequest;
import org.dongguk.dambo.dto.auth.SignUpRequest;
import org.dongguk.dambo.dto.jwt.JwtTokensDto;
import org.dongguk.dambo.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/sign-up")
    public ResponseEntity<JwtTokensDto> signUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<JwtTokensDto> signIn(
            @RequestBody LoginRequest loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest.loginId(), loginRequest.password()));
    }

    @PostMapping("/users/check-id")
    public ResponseEntity<Boolean> checkDuplicate(
            @RequestBody CheckDuplicateIdRequest request
    ) {
        authService.checkDuplicateId(request.loginId());

        return ResponseEntity.ok(Boolean.TRUE);
    }
}
