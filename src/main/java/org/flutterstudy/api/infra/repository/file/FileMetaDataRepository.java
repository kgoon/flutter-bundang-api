package org.flutterstudy.api.infra.repository.file;

import org.flutterstudy.api.domain.file.FileMetaData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Repository
public class FileMetaDataRepository {
	public void save(FileMetaData metaData) {
		ofy().save().entity(metaData);
	}

	public Optional<FileMetaData> get(Long fileId) {
		return Optional.ofNullable(ofy().load().type(FileMetaData.class).id(fileId).now());
	}
}
