package org.flutterstudy.api.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RuntimeContextPhase {

	UNIT_TEST("unit"),
	LOCAL_DEV("local"),
	TEST_DEV("test"),
	TEST_BRANCH("test"),
	STAGING("default"),
	RELEASE_CANDIDATE("default"),
	RELEASE_ACTIVE("default");

	private String namespace;
}
