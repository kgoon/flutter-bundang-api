package org.flutterstudy.api.controller;

import org.flutterstudy.api.config.security.AuthenticationTokenProvider;
import org.flutterstudy.api.config.security.JwtTokenProvider;
import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.domain.user.entity.UserBase;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.model.dto.AuthenticationToken;
import org.flutterstudy.api.model.dto.LoginRequest;
import org.flutterstudy.api.model.dto.RegisterFormData;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.flutterstudy.api.config.security.annotations.CurrentUser;
import org.flutterstudy.api.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

	private AuthenticationTokenProvider authTokenProvider;

	private UserService userService;

	@GetMapping(value = "/hello")
	ResponseEntity<String> hello(){
		return ResponseEntity.ok("Hello!!");
	}


	@PostMapping(value = "/register")
	ResponseEntity<AuthenticationToken> register(RegisterFormData registerForm){
		// TODO : 도메인이 서비스 밖으로 나오지 않도록 수정
		User user = userService.register(registerForm);
		return ResponseEntity.ok(authTokenProvider.create(user));
	}

	@GetMapping("/")
	ResponseEntity<Model> home(@CurrentUser AuthenticationUser user, Model model){

		if (user != null) {
			model.addAttribute("user_info", user.getId());
			model.addAttribute("user_email", user.getUsername());
		}

		return ResponseEntity.ok(model);
	}

	@GetMapping(value = "/email/exist")
	ResponseEntity<Map<String, Boolean>> isExistEmail(){
		Map<String, Boolean> result = new HashMap<>();
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/login")
	ResponseEntity<AuthenticationToken> login(LoginRequest loginRequest) throws UnsupportedEncodingException {
		return ResponseEntity.ok(userService.getAuthToken(loginRequest));
	}
}
