package org.flutterstudy.api.test.util;

import com.google.cloud.datastore.testing.LocalDatastoreHelper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;

@AllArgsConstructor
public class LocalDatastoreExtension implements BeforeAllCallback, BeforeEachCallback {

	private static final double MAX_EVENTUAL_CONSISTENCY = 1.0d;

	private double consistency;

	public LocalDatastoreExtension() {
		this(MAX_EVENTUAL_CONSISTENCY);
	}

	@Override
	public void beforeAll(final ExtensionContext context) throws Exception {
		if (getHelper(context) == null) {
			final LocalDatastoreHelper helper = LocalDatastoreHelper.create(consistency);
			context.getRoot().getStore(Namespace.GLOBAL).put(LocalDatastoreHelper.class, helper);
			helper.start();
		}

	}

	@Override
	public void beforeEach(final ExtensionContext context) throws Exception {
		final LocalDatastoreHelper helper = getHelper(context);
		helper.reset();
	}

	/** Get the helper created in beforeAll; it should be global so there will one per test run */
	public static LocalDatastoreHelper getHelper(final ExtensionContext context) {
		return context.getRoot().getStore(Namespace.GLOBAL).get(LocalDatastoreHelper.class, LocalDatastoreHelper.class);
	}
}
