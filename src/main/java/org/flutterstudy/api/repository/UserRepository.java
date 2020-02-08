package org.flutterstudy.api.repository;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.model.EmailAddress;

import java.util.Optional;

public interface UserRepository {
	void save(User user);

	Optional<User> findByIdentifier(EmailAddress emailAddress);
}
