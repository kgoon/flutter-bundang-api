package org.flutterstudy.api.controller;

import lombok.AllArgsConstructor;
import org.flutterstudy.api.config.security.AuthenticationTokenProvider;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.config.security.annotations.CurrentUser;
import org.flutterstudy.api.contracts.vo.EmailAddress;
import org.flutterstudy.api.contracts.dto.request.LoginRequest;
import org.flutterstudy.api.contracts.dto.request.RegisterFormData;
import org.flutterstudy.api.contracts.vo.UserName;
import org.flutterstudy.api.contracts.dto.response.AuthenticationToken;
import org.flutterstudy.api.contracts.dto.response.SimpleBooleanResponse;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

	private AuthenticationTokenProvider authTokenProvider;

	private UserService userService;


	@PostMapping(value = "/register")
	ResponseEntity<AuthenticationToken> register(RegisterFormData registerForm){
		// TODO : 도메인이 서비스 밖으로 나오지 않도록 수정
		User user = userService.register(registerForm);
		return ResponseEntity.ok(authTokenProvider.create(user));
	}

	@PutMapping(value = "/profile/name")
	ResponseEntity<?> setUserName(
			@CurrentUser AuthenticationUser user,
			@Valid UserName userName,
			BindingResult result
	) {
		if(result.hasErrors()){
			return ResponseEntity.badRequest().body(result.getFieldError());
		}

		userService.setUserName(user.getId(), userName);
		return ResponseEntity.ok(SimpleBooleanResponse.of(true));
	}

	@GetMapping(value = "/profile/name/exist")
	ResponseEntity<SimpleBooleanResponse> isExistUserName(String userName){
		boolean result = userService.getIdentifier(new UserName(userName)).isPresent();
		return ResponseEntity.ok(SimpleBooleanResponse.of(result));
	}

	@GetMapping(value = "/email/exist")
	ResponseEntity<SimpleBooleanResponse> isExistEmail(@RequestParam("email") EmailAddress email){
		boolean result = userService.getIdentifier(email).isPresent();
		return ResponseEntity.ok(SimpleBooleanResponse.of(result));
	}

	@PostMapping(value = "/login")
	ResponseEntity<AuthenticationToken> login(LoginRequest loginRequest) throws UnsupportedEncodingException {
		return ResponseEntity.ok(userService.getAuthToken(loginRequest));
	}
}
