package org.flutterstudy.api;

import org.flutterstudy.api.config.WebMvcConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Import(WebMvcConfiguration.class)
public class Application {

    private static final String PROPERTIES =
            "spring.config.additional-location=classpath:/secure/";

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .properties(PROPERTIES)
                .run(args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}