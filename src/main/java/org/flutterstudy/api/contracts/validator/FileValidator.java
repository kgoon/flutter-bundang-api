package org.flutterstudy.api.contracts.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class FileValidator<T extends Annotation> implements ConstraintValidator<T, MultipartFile> {

	@Override
	public void initialize(T constraintAnnotation) {
		// do nothing
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
		if(multipartFile.isEmpty()){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					"File must be not empty")
					.addConstraintViolation();
			return false;
		}

		return true;
	}
}