package org.flutterstudy.api.repository;

import org.flutterstudy.api.domain.user.User;

import java.util.Optional;

public interface UserRepository {
	User findByEmail(String email);

	void save(User user);

	Optional<User> findById(Long userId);
}
