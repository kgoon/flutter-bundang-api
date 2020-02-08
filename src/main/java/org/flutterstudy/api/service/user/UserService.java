package org.flutterstudy.api.service.user;

import org.flutterstudy.api.config.security.AuthenticationTokenProvider;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.model.EmailAddress;
import org.flutterstudy.api.model.dto.AuthenticationToken;
import org.flutterstudy.api.model.dto.LoginRequest;
import org.flutterstudy.api.model.dto.RegisterFormData;
import org.flutterstudy.api.repository.UserRepository;
import org.flutterstudy.api.service.exception.AlreadyExistUser;
import org.flutterstudy.api.service.exception.NotFoundMatchedUser;
import org.flutterstudy.api.service.identifier.IdentifierProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final IdentifierProvider<Long> identifierProvider;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationTokenProvider authTokenProvider;

    public AuthenticationToken getAuthToken(LoginRequest loginRequest) {
        User user = userRepository.findByIdentifier(new EmailAddress(loginRequest.getEmail()))
            .orElseThrow(() -> new NotFoundMatchedUser("there is no information about this email. - " + loginRequest.getEmail()));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotFoundMatchedUser("password is not match about this email.");
        }
        return authTokenProvider.create(user);
    }

    public User register(RegisterFormData registerForm) {
        Optional<User> existUser = userRepository.findByIdentifier(new EmailAddress(registerForm.getEmail()));
        if(existUser.isPresent()){
            throw new AlreadyExistUser();
        }

        User user = User.builder(identifierProvider.create(UserBase.class), registerForm.getBirth())
                .addIdentifier(new EmailAddress(registerForm.getEmail()), passwordEncoder.encode(registerForm.getPassword()))
                .build();

        userRepository.save(user);

        return user;
    }
}