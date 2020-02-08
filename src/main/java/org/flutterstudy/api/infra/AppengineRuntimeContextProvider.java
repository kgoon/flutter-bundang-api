package org.flutterstudy.api.infra;

import com.google.appengine.api.modules.ModulesServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import org.springframework.stereotype.Component;

@Component
public class AppengineRuntimeContextProvider implements ServiceRuntimeContextProvider {

	private static final String DEFAULT_SERVICE_NAME = "default";

	private RuntimeContextPhase phase;

	public RuntimeContextPhase getPhase(){
		if(phase != null){
			return phase;
		}

		if(SystemProperty.environment.value() == SystemProperty.Environment.Value.Development){
			phase = RuntimeContextPhase.LOCAL_DEV;
			return phase;
		}

		String defaultVersion = ModulesServiceFactory.getModulesService().getDefaultVersion(DEFAULT_SERVICE_NAME);
		String currentVersion = ModulesServiceFactory.getModulesService().getCurrentVersion();

		if(defaultVersion.equals(currentVersion)){
			phase = RuntimeContextPhase.RELEASE_ACTIVE;
		}else if(currentVersion.equals("dev")){
			phase = RuntimeContextPhase.TEST_DEV;
		}else if(currentVersion.contains("test")){
			phase = RuntimeContextPhase.TEST_DEV;
		}else if(currentVersion.equals("staging")){
			phase = RuntimeContextPhase.STAGING;
		}else if(currentVersion.contains("release")){
			phase = RuntimeContextPhase.RELEASE_CANDIDATE;
		}else{
			throw new IllegalStateException("Unexpected app version : " + currentVersion);
		}

		return phase;
	}
}
