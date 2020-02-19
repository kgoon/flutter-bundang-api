package org.flutterstudy.api.contracts.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidatorContext;

/**
 * 파일형식을 기준으로 유효한 이미지파일 검증
 * https://stackoverflow.com/questions/40355743/file-upload-in-spring-boot-uploading-validation-and-exception-handling
 */
public class ImageFileValidator extends FileValidator<ValidImage> {

	@Override
	public void initialize(ValidImage constraintAnnotation) {
		// do nothing
	}

	@Override
	public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {

		if(!super.isValid(multipartFile, context)){
			return false;
		}

		if (!isSupportedContentType(multipartFile.getContentType())) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
					"Only PNG or JPG images are allowed.")
					.addConstraintViolation();

			return false;
		}

		return true;
	}

	private boolean isSupportedContentType(String contentType) {
		return contentType.equals("image/png")
				|| contentType.equals("image/jpg")
				|| contentType.equals("image/jpeg");
	}
}