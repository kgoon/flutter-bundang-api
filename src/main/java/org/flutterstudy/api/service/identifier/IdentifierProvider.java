package org.flutterstudy.api.service.identifier;

public interface IdentifierProvider<T> {

	T create(Class entity);
}
