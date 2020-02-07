package org.flutterstudy.api.config;

import com.googlecode.objectify.ObjectifyFilter;
import org.flutterstudy.api.infra.ServiceRuntimeContextProvider;
import org.flutterstudy.api.infra.listener.ObjectifyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.flutterstudy.api"})
public class WebMvcConfiguration {

	@Autowired
	ServiceRuntimeContextProvider runtimeContextProvider;

	/**
	 * Objectify requires a filter to clean up any thread-local transaction contexts
	 * and pending asynchronous operations that remain at the end of a request.
	 * https://github.com/takemikami/spring-boot-objectify-sample/blob/master/src/main/java/objectifysample/ObjectifyConfig.java
	 *
	 * @return ObjectifyFilter with registration bean
	 */
	@Bean
	public FilterRegistrationBean<ObjectifyFilter> objectifyFilterRegistration() {
		// TODO : why define with 'final' ?
		final FilterRegistrationBean<ObjectifyFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new ObjectifyFilter());
		registration.addUrlPatterns("/*");
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public ServletListenerRegistrationBean<ObjectifyListener> listenerRegistrationBean() {
		ServletListenerRegistrationBean<ObjectifyListener> listener =
				new ServletListenerRegistrationBean<>();
		listener.setListener(new ObjectifyListener(runtimeContextProvider.getPhase()));
		return listener;
	}
}
