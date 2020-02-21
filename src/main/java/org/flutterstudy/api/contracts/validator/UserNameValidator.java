package org.flutterstudy.api.contracts.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class UserNameValidator implements ConstraintValidator<ValidUserName, String> {

	private static final Pattern allowUserNamePattern;

	static {
		allowUserNamePattern = Pattern.compile("^[a-z0-9_.-]{3,16}$");
	}

	@Override
	public void initialize(ValidUserName constraintAnnotation) {
		// do nothing
	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		if (!isAllowedName(userName)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					"Username allows only alphabet, _, - or '.' and length between 3 and 16")
					.addConstraintViolation();

			return false;
		}

		return true;
	}

	public static boolean isAllowedName(String value){
		return allowUserNamePattern.matcher(value).matches();
	}
}
