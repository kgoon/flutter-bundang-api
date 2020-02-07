package org.flutterstudy.api.infra.repository.identifier;

import org.flutterstudy.api.service.identifier.IdentifierProvider;
import org.springframework.stereotype.Repository;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Repository
public class LongTypeIdentifierDatastoreRepository implements IdentifierProvider<Long> {

	@Override
	public Long create(Class entity) {
		LongTypeIdentifier identifier = new LongTypeIdentifier(entity.getSimpleName());
		ofy().save().entity(identifier).now();

		return identifier.getValue();
	}
}
