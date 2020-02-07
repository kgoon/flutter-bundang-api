package org.flutterstudy.api.test.util;

import org.flutterstudy.api.infra.RuntimeContextPhase;
import org.flutterstudy.api.infra.ServiceRuntimeContextProvider;

public class TestRuntimeContextProvider implements ServiceRuntimeContextProvider {

	@Override
	public RuntimeContextPhase getPhase() {
		return RuntimeContextPhase.UNIT_TEST;
	}
}
