package org.flutterstudy.api.service;


import lombok.RequiredArgsConstructor;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/*
* For Spring Security 사용을 위해 꼭 필요한 UserDetailsService를 구현한 클래스입니다.
* 실제로 사용되지는 않지만, 내부 동작에 필요하기 때문에 삭제하면 안됩니다.(차후 추가구현 및 수정 Ok)
*/
@RequiredArgsConstructor
@Service

public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        User user = userRepository.findByEmail((userEmail));
        AuthenticationUser authenticationUser = new AuthenticationUser(user.getId(), user.getRoles());
        return authenticationUser;
    }
}
