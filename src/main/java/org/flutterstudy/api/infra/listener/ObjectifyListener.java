package org.flutterstudy.api.infra.listener;

import com.google.appengine.api.NamespaceManager;
import com.googlecode.objectify.ObjectifyService;
import org.flutterstudy.api.domain.file.FileMetaData;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.domain.user.entity.UserIdentifier;
import org.flutterstudy.api.infra.RuntimeContextPhase;
import org.flutterstudy.api.infra.repository.identifier.LongTypeIdentifier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ObjectifyListener implements ServletContextListener {

	public ObjectifyListener(RuntimeContextPhase phase) {
		if(!phase.equals(RuntimeContextPhase.UNIT_TEST)) {
			NamespaceManager.set(phase.getNamespace());
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ObjectifyService.init();
		ObjectifyService.register(LongTypeIdentifier.class);
		ObjectifyService.register(UserBase.class);
		ObjectifyService.register(UserIdentifier.class);
		ObjectifyService.register(FileMetaData.class);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
}
