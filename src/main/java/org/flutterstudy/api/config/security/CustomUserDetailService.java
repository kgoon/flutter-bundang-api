package org.flutterstudy.api.config.security;


import lombok.RequiredArgsConstructor;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.model.EmailAddress;
import org.flutterstudy.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service

public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        User user = userRepository.findByIdentifier(new EmailAddress(userEmail))
                .orElseThrow(() -> new UsernameNotFoundException("Not found user by email(" + userEmail +")"));
        return new AuthenticationUser(user.getPrimaryId(), user.getRoles());
    }
}
