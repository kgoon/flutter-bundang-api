package org.flutterstudy.api.infra.adapter;

import com.google.cloud.storage.*;
import org.flutterstudy.api.contracts.dto.FileMetaData;
import org.flutterstudy.api.contracts.enums.AttachUseType;
import org.flutterstudy.api.infra.ServiceRuntimeContextProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleCloudStorageAdapter implements FileStorageAdapter {
	private static final String STORAGE_HOST = "https://storage.googleapis.com";
	private static final String PATH_DIVIDER = "/";

	private final String namespacePathPrefix;

	private final String userFileBucketName;

	private final String storageBaseURL;

	public GoogleCloudStorageAdapter(@Value("${gcloud.bucket.name}") String userFileBucketName, ServiceRuntimeContextProvider runtimeContextProvider) {
		this.userFileBucketName = userFileBucketName;
		this.namespacePathPrefix = "service/" + runtimeContextProvider.getPhase().getNamespace();
		this.storageBaseURL = new StringBuilder().append(STORAGE_HOST)
				.append(PATH_DIVIDER)
				.append(this.userFileBucketName)
				.append(PATH_DIVIDER)
				.append(this.namespacePathPrefix)
				.append(PATH_DIVIDER)
				.toString();
	}

	@Override
	public Blob upload(FileMetaData file, byte[] bytes) {
		List<Acl> aclList = new ArrayList<>(); //Access Role List
		aclList.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

		Storage storage = StorageOptions.getDefaultInstance().getService();
		String objectName = this.namespacePathPrefix + PATH_DIVIDER + getPath(file);
				BlobInfo blobInfo = BlobInfo.newBuilder(this.userFileBucketName, objectName)
				.setAcl(aclList)
				.build();

		Blob blob = storage.create(blobInfo, bytes);

		return blob;
	}

	public String getFileURI(FileMetaData file){
		return this.storageBaseURL + PATH_DIVIDER + getPath(file);
	}


	private String getPath(FileMetaData file){
		// notice : /로 시작하지 않는다. (path가 /로 시작하게 되면 -> bucketName//path가 됨.)
		// TODO : useType 값을 기준으로 PATH가 결정되도록 변경
		return new StringBuilder()
				.append(file.getOwnerId())
				.append(PATH_DIVIDER)
				.append(file.getUseType().toString().toLowerCase())
				.append(PATH_DIVIDER)
				.append(file.getId())
				.toString();
	}
}
