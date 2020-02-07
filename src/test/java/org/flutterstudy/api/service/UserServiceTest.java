package org.flutterstudy.api.service;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.model.dto.LoginRequest;
import org.flutterstudy.api.model.dto.RegisterFormData;
import org.flutterstudy.api.infra.repository.identifier.LongTypeIdentifier;
import org.flutterstudy.api.repository.UserRepository;
import org.flutterstudy.api.service.exception.NotFoundMatchedUser;
import org.flutterstudy.api.test.util.LocalDatastoreExtension;
import org.flutterstudy.api.test.util.ObjectifyExtension;
import org.flutterstudy.api.test.util.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.googlecode.objectify.ObjectifyService.factory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith({SpringExtension.class,
        LocalDatastoreExtension.class,
        ObjectifyExtension.class})
@Import(TestConfig.class)
@DataJpaTest
public class UserServiceTest {

    private final String testEmail = "test@kkongtents.com";
    private final String testPassword = "100k";
    private final String unregisteredEmail = "guest@kkongtents.com";
    private final String wrongPassword = "200k";

    @Autowired
	UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void makeTestUser(){
        factory().register(LongTypeIdentifier.class);
        userService.register(new RegisterFormData("tester", testEmail, testPassword));
    }

    @Test
    public void matchValid() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(testEmail);
        loginRequest.setPassword(testPassword);

        // Action
        User user = userService.getMatchedUser(loginRequest);

        // Assertion
        assertThat(user.getEmail()).isEqualTo(testEmail);
    }

    @Test
    public void matchWithInvalidPassword(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(testEmail);
        loginRequest.setPassword(wrongPassword);

        assertThatExceptionOfType(NotFoundMatchedUser.class)
                .isThrownBy(() -> {
                    userService.getMatchedUser(loginRequest);
                }).withMessage("password is not match about this email.");
    }

    @Test
    public void matchWithInvalidEmail(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(unregisteredEmail);
        loginRequest.setPassword(testPassword);

        assertThatExceptionOfType(NotFoundMatchedUser.class)
                .isThrownBy(() -> {
                    userService.getMatchedUser(loginRequest);
                }).withMessage("there is no information about this email.");
    }

}