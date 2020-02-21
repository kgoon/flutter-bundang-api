package org.flutterstudy.api.contracts.dto.response;

import lombok.Value;

@Value
public class SimpleBooleanResponse {
	boolean result;

	public static SimpleBooleanResponse of(boolean value) {
		return new SimpleBooleanResponse(value);
	}
}
