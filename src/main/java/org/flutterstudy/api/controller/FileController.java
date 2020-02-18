package org.flutterstudy.api.controller;

import lombok.AllArgsConstructor;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.config.security.annotations.CurrentUser;
import org.flutterstudy.api.contracts.dto.FileMetaData;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.contracts.dto.ImageFile;
import org.flutterstudy.api.contracts.enums.AttachUseType;
import org.flutterstudy.api.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {

	FileUploadService fileUploadService;

	@PostMapping("/avatar/{userId}")
	ResponseEntity<?> uploadUserAvatar(
			@CurrentUser AuthenticationUser user,
			@Valid ImageFile imageFile,
			BindingResult result
	) throws IOException {
		if(result.hasErrors()){
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}

		MultipartFile file = imageFile.getFile();

		FileMetaData fileMetaData = fileUploadService
				.uploadFile(
						User.class,
						AttachUseType.AVATAR,
						file.getOriginalFilename(),
						file.getBytes()
				);

		return ResponseEntity.ok(fileMetaData);
	}
}
