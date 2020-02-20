package org.flutterstudy.api.contracts.dto;

import lombok.Value;
import org.flutterstudy.api.contracts.validator.ValidUserName;

import javax.validation.constraints.NotNull;

@Value
public class UserName {
	@NotNull
	@ValidUserName
	String value;
}
