package org.flutterstudy.api.infra.listener;

import com.google.appengine.api.NamespaceManager;
import com.googlecode.objectify.ObjectifyService;
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
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// do nothing
	}
}
