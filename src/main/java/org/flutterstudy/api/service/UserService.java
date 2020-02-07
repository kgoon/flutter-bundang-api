package org.flutterstudy.api.service;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.model.dto.LoginRequest;
import org.flutterstudy.api.model.dto.RegisterFormData;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.flutterstudy.api.repository.UserRepository;
import org.flutterstudy.api.service.exception.AlreadyExistUser;
import org.flutterstudy.api.service.exception.NotFoundMatchedUser;
import org.flutterstudy.api.service.identifier.IdentifierProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final IdentifierProvider<Long> identifierProvider;

    private final PasswordEncoder passwordEncoder;

    public User getMatchedUser(LoginRequest userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            throw new NotFoundMatchedUser("there is no information about this email.");
        }

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new NotFoundMatchedUser("password is not match about this email.");
        }
        return user;
    }

    public User register(RegisterFormData registerForm) {
        User existUser = userRepository.findByEmail(registerForm.getEmail());
        if(existUser != null){
            throw new AlreadyExistUser();
        }

        User user = new User(
                identifierProvider.create(User.class),
                registerForm.getEmail(),
                registerForm.getUserName(),
                passwordEncoder.encode(registerForm.getPassword())
        );

        userRepository.save(user);

        return user;
    }

    public User appendRole(Long userId, UserRole role) {
        User targetUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundMatchedUser("Not found user matched with id"));
        targetUser.addRole(role);
        userRepository.save(targetUser);

        return targetUser;
    }
}
