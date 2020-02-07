package org.flutterstudy.api.service.cookie;

import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.service.exception.NotFoundJwtCookie;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtCookieHandlerTest {

    private final String cookiePath = "/";
    private JwtCookieHandler jwtCookieHandler =  new JwtCookieHandler(new JwtTokenProvider("testSecretKey"));
    private Cookie[] cookieTestArray = new Cookie[1];;
    private int LIFE_TIME_OF_DEAD_COOKIE = 0;

    @Before
    public void justMakeTestCookie() throws UnsupportedEncodingException {
        User testUser = new User(101L, "test@kkongtents.com", "tester", "password");
        cookieTestArray[0] = jwtCookieHandler.make(testUser);
    }

    @Test
    public void makeCookieValid(){
        assertThat(cookieTestArray[0].getPath()).isEqualTo(cookiePath);
    }

    @Test
    public void readCookieArray() throws NotFoundJwtCookie {
        Assertions.assertThat(jwtCookieHandler.readToken(cookieTestArray)).isNotNull();
    }

    @Test
    public void deleteCookie(){
       Cookie passedAwayCookie = jwtCookieHandler.delete();
       assertThat(passedAwayCookie.getMaxAge()).isLessThanOrEqualTo(LIFE_TIME_OF_DEAD_COOKIE);
    }
}