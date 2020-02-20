package org.flutterstudy.api.repository;

import org.flutterstudy.api.contracts.dto.UserName;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.contracts.EmailAddress;

import java.util.Optional;

public interface UserRepository {
	void save(User user);

	Optional<User> findByIdentifier(EmailAddress emailAddress);

	Optional<User> findByIdentifier(UserName userName);

	User get(Long userId);
}
