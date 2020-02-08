package org.flutterstudy.api.config.security;

import io.jsonwebtoken.JwtException;
import org.flutterstudy.api.service.exception.NotFoundToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationTokenFilter extends GenericFilterBean {

    private static final String AUTH_TOKEN_HEADER = "Authorization";
    private AuthenticationTokenProvider tokenProvider;

    public AuthenticationTokenFilter(AuthenticationTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * Request에 인증용 JWT 쿠키가 포함된 경우 유효성을 검증하고 인증절차 진행
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            UserClaims userClaims = tokenProvider.parse(readToken((HttpServletRequest) request));
            if (userClaims != null && userClaims.isValidate()) {
                Authentication auth = userClaims.getAuthentication();
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (NotFoundToken e){
            // do nothing (비로그인 요청)
        } catch (InvalidTokenException e) {
            // 비밀키 변경, 유효기간 초과 등의 이유로 토큰이 유효하지 않을 경우 쿠키 삭제
        }

        filterChain.doFilter(request, response);
    }
    
    private String readToken(HttpServletRequest request) throws NotFoundToken{
        String token = request.getHeader(AUTH_TOKEN_HEADER);
        if(token == null){
            throw new NotFoundToken();
        }

        return token;
    }
}
