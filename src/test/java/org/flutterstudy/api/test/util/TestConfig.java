package org.flutterstudy.api.test.util;

import org.flutterstudy.api.infra.ServiceRuntimeContextProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestConfig {
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	@Primary
	public ServiceRuntimeContextProvider getRuntimeContextProvider(){
		return new TestRuntimeContextProvider();
	}
}
