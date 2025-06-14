package org.dongguk.dambo.core.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.core.exception.GlobalErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException
    ) throws IOException {
        GlobalErrorCode errorCode = (GlobalErrorCode) request.getAttribute("exception");
        if (errorCode == null) {
            errorCode = GlobalErrorCode.ACCESS_DENIED_ERROR;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());

        BaseResponse<?> baseResponse = BaseResponse.fail(errorCode);

        response.getWriter().write(
                objectMapper.writeValueAsString(baseResponse)
        );
    }

}