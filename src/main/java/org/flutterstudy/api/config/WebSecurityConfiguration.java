package org.flutterstudy.api.config;


import lombok.AllArgsConstructor;
import org.flutterstudy.api.config.security.JwtAuthenticationFilter;
import org.flutterstudy.api.service.cookie.JwtCookieHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtCookieHandler jwtCookieHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
//                .csrfTokenRepository(new CookieCsrfTokenRepository())
//            .and()
            .authorizeRequests()
                .antMatchers("/api/user/logout").hasRole("USER")
                .anyRequest().permitAll()
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtCookieHandler), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}