package org.flutterstudy.api.service.cookie;

import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.config.security.UserClaims;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.service.exception.NotFoundJwtCookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;


@AllArgsConstructor
@Component
public class JwtCookieHandler {

    private static final String JWT_COOKIE_NAME = "jwt";

    private final JwtTokenProvider jwtTokenProvider;

    public UserClaims readToken(Cookie[] cookies) throws NotFoundJwtCookie {

        if(cookies == null){
            throw new NotFoundJwtCookie();
        }

        for (Cookie cookie : cookies) {
            if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                String value = cookie.getValue();
                return jwtTokenProvider.parse(value);
            }
        }

        throw new NotFoundJwtCookie();
    }

    public Cookie make(User user) throws UnsupportedEncodingException {
        String value = jwtTokenProvider.createToken(user);;
        Cookie cookie = new Cookie(JWT_COOKIE_NAME, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie delete(){
        Cookie cookie = new Cookie(JWT_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        return cookie;
    }


}
