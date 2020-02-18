package org.flutterstudy.api.infra.repository.file;

import org.flutterstudy.api.contracts.dto.FileMetaData;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class FileMetaDataRepository {
	public void save(FileMetaData metaData) {
		ofy().save().entity(metaData);
	}
}
