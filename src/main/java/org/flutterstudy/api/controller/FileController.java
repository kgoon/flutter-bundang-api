package org.flutterstudy.api.controller;

import lombok.AllArgsConstructor;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.config.security.annotations.CurrentUser;
import org.flutterstudy.api.contracts.dto.FileMetaData;
import org.flutterstudy.api.contracts.dto.ImageFile;
import org.flutterstudy.api.contracts.enums.AttachUseType;
import org.flutterstudy.api.service.FileUploadService;
import org.flutterstudy.api.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/file")
public class FileController {

	FileUploadService fileUploadService;

	UserService userService;

	@PostMapping("/avatar")
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
				.uploadUserFile(
						user.getId(),
						AttachUseType.AVATAR,
						file.getOriginalFilename(),
						file.getBytes()
				);

		userService.setAvatar(user.getId(), fileMetaData);

		return ResponseEntity.ok(fileMetaData);
	}

	@GetMapping("/avatar/{userId}")
	ResponseEntity<?> getUserAvatar(@PathVariable Long userId){
		return ResponseEntity.ok().build();
	}
}
