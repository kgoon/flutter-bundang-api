package org.flutterstudy.api.contracts.dto.request;

import lombok.Data;
import org.flutterstudy.api.contracts.validator.ValidImage;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

@Data
public class ImageFile {
	@NotNull
	@ValidImage
	MultipartFile file;
}
