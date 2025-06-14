package org.dongguk.dambo.core.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dongguk.dambo.core.common.BaseResponse;
import org.dongguk.dambo.core.exception.CustomException;
import org.dongguk.dambo.core.exception.ErrorCode;
import org.dongguk.dambo.core.exception.GlobalErrorCode;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            handleException(response, GlobalErrorCode.TOKEN_MALFORMED_ERROR, e);
        } catch (IllegalArgumentException e) {
            handleException(response, GlobalErrorCode.TOKEN_TYPE_ERROR, e);
        } catch (ExpiredJwtException e) {
            handleException(response, GlobalErrorCode.EXPIRED_TOKEN_ERROR, e);
        } catch (UnsupportedJwtException e) {
            handleException(response, GlobalErrorCode.TOKEN_UNSUPPORTED_ERROR, e);
        } catch (JwtException e) {
            handleException(response, GlobalErrorCode.TOKEN_UNKNOWN_ERROR, e);
        } catch (CustomException e) {
            handleException(response, e.getErrorCode(), e);
        } catch (Exception e) {
            handleException(response, GlobalErrorCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private void handleException(
            HttpServletResponse response,
            ErrorCode errorCode,
            Exception e
    ) throws IOException {
        log.error("[JwtExceptionFilter] 예외 발생 : {}", e.getMessage(), e);

        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse<?> baseResponse = BaseResponse.fail(errorCode);

        response.getWriter().write(
                objectMapper.writeValueAsString(baseResponse)
        );

        return;
    }

}
