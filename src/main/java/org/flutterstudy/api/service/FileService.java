package org.flutterstudy.api.service;

import lombok.AllArgsConstructor;
import org.flutterstudy.api.domain.file.FileMetaData;
import org.flutterstudy.api.contracts.enums.AttachUseType;
import org.flutterstudy.api.contracts.enums.StorageType;
import org.flutterstudy.api.infra.adapter.FileStorageAdapter;
import org.flutterstudy.api.infra.repository.file.FileMetaDataRepository;
import org.flutterstudy.api.service.exception.NotFoundFileException;
import org.flutterstudy.api.service.identifier.IdentifierProvider;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {

	private final FileStorageAdapter fileStorageAdapter;

	private final FileMetaDataRepository fileMetaDataRepository;

	private final IdentifierProvider<Long> identifierProvider;

	public FileMetaData uploadUserFile(Long ownerId, AttachUseType useType, String fileName, String contentType, byte[] bytes) {
		Long fileId = identifierProvider.create(FileMetaData.class);
		long fileSize = bytes.length;

		FileMetaData metaData = new FileMetaData(fileId,
				ownerId,
				useType,
				fileName,
				contentType,
				fileSize,
				StorageType.CLOUD_STORAGE_USER_BUCKET);

		fileStorageAdapter.upload(metaData, bytes);
		fileMetaDataRepository.save(metaData);
		return metaData;
	}

	public String getServeUri(Long fileId){
		FileMetaData metaData = fileMetaDataRepository.get(fileId).orElseThrow(() -> new NotFoundFileException());
		return fileStorageAdapter.getFileURI(metaData);
	}
}
