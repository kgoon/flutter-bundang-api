package org.flutterstudy.api.contracts.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import lombok.*;
import org.flutterstudy.api.contracts.enums.AttachUseType;
import org.flutterstudy.api.contracts.enums.StorageType;

/**
 * 업로드된 파일의 위치정보, 부가정보(이름, 크기 등)를 기록하기 위한 엔티티
 */
@Getter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class FileMetaData {

	@Id
	private final Long id;

	@Index
	private final  Long ownerId;

	private final  AttachUseType useType;

	private final  String fileName;

	private final  long size;

	@JsonIgnore
	private StorageType storageType = StorageType.CLOUD_STORAGE_USER_BUCKET; //default;

}
