package org.flutterstudy.api.infra.repository.identifier;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.test.util.LocalDatastoreExtension;
import org.flutterstudy.api.test.util.ObjectifyExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.googlecode.objectify.ObjectifyService.factory;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({
		LocalDatastoreExtension.class,
		ObjectifyExtension.class
})
class LongTypeIdentifierDatastoreRepositoryTest {

	LongTypeIdentifierDatastoreRepository repository;

	@BeforeEach
	void setup(){
		factory().register(LongTypeIdentifier.class);
		repository = new LongTypeIdentifierDatastoreRepository();
	}

	@Test
	void create() {
		Long firstId = repository.create(User.class);
		Long secondId = repository.create(User.class);

		assertThat(firstId).isNotNull();
		assertThat(firstId).isNotEqualTo(secondId);
	}
}