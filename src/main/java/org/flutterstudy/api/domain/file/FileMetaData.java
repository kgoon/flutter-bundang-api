package org.flutterstudy.api.domain.file;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class FileMetaData {

	@Id
	private Long id;

	@Index
	private Long ownerId;

	private AttachUseType useType;

	private String fileName;

	private String contentType;

	private long size;

	@JsonIgnore
	private StorageType storageType; //default;

}
