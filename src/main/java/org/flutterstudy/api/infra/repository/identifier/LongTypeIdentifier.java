package org.flutterstudy.api.infra.repository.identifier;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
public class LongTypeIdentifier {

	@Id
	private Long id;

	@Getter
	private String entityName;

	private Date createAt;

	public LongTypeIdentifier(String entityName) {
		this.entityName = entityName;
		this.createAt = new Date();
	}

	public Long getValue(){
		return id;
	}
}
