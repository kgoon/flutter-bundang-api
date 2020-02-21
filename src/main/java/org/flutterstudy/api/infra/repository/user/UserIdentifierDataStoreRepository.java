package org.flutterstudy.api.infra.repository.user;

import org.flutterstudy.api.domain.user.entity.UserIdentifier;
import org.flutterstudy.api.domain.user.enums.UserIdentifierType;
import org.flutterstudy.api.repository.ExistEntityException;
import org.flutterstudy.api.repository.NotFoundEntityException;

import java.util.List;
import java.util.Optional;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class UserIdentifierDataStoreRepository{
    public void create(UserIdentifier identifier) {
        if(ofy().load().entity(identifier).now() != null){
            throw new ExistEntityException();
        }

        ofy().save().entity(identifier);
    }

    public Long getPrimaryId(UserIdentifierType idType, String value) {
        String idLiteral = UserIdentifier.getLiteral(idType, value);
        UserIdentifier identifier = ofy()
                .load()
                .type(UserIdentifier.class)
                .id(idLiteral)
                .now();

        return Optional.ofNullable(identifier)
                .orElseThrow(() -> new NotFoundEntityException())
                .getPrimaryId();
    }

	public List<UserIdentifier> findByPrimaryId(Long userId) {
        return ofy().load()
                .type(UserIdentifier.class)
                .filter("primaryId", userId)
                .list();
	}
}
