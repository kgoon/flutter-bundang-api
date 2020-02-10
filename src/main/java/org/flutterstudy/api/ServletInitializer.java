package org.flutterstudy.api;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	private static final String ADDITIONAL_PROPERTIES =
			"spring.config.additional-location=classpath:/secure/";

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		application.properties(ADDITIONAL_PROPERTIES);
		return application.sources(Application.class);
	}
}
