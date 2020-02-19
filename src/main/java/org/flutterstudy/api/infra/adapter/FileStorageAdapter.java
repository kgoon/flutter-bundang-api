package org.flutterstudy.api.infra.adapter;

import com.google.cloud.storage.Blob;
import org.flutterstudy.api.contracts.dto.FileMetaData;

public interface FileStorageAdapter {
	Blob upload(FileMetaData file, byte[] bytes);

	String getFileURI(FileMetaData file);
}
