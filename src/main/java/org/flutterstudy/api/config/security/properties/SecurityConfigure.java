package org.flutterstudy.api.config.security.properties;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

@Value
@ConstructorBinding
@ConfigurationProperties("app.security")
public class SecurityConfigure {
	JwtKey jwt;

	ApplicationOwner owner;
}

