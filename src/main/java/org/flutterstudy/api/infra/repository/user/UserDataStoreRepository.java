package org.flutterstudy.api.infra.repository.user;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDataStoreRepository implements UserRepository {
	public User findByEmail(String email) {
		return null;
	}

	public void save(User user) {

	}

	public Optional<User> findById(Long userId) {
		return null;
	}
}
