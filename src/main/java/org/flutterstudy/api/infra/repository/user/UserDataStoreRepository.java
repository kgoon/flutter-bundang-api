package org.flutterstudy.api.infra.repository.user;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.domain.user.enums.UserIdentifierType;
import org.flutterstudy.api.model.EmailAddress;
import org.flutterstudy.api.repository.NotFoundEntityException;
import org.flutterstudy.api.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Repository
public class UserDataStoreRepository implements UserRepository {

	UserIdentifierDataStoreRepository identifierRepository;

	UserDataStoreRepository(){
		identifierRepository = new UserIdentifierDataStoreRepository();
	}

	@Override
	public void save(User user) {
		ofy().save().entities(user.getEntities());
	}

	@Override
	public Optional<User> findByIdentifier(EmailAddress emailAddress) {
		try {
			Long primaryId = identifierRepository.getPrimaryId(UserIdentifierType.EMAIL, emailAddress.getValue());
			UserBase userBase = ofy().load().type(UserBase.class).id(primaryId).now();
			return Optional.of(User.of(userBase));
		}catch (NotFoundEntityException e){
			return Optional.empty();
		}
	}
}
