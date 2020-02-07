package org.flutterstudy.api.controller.rest;

import org.flutterstudy.api.domain.user.User;
import org.flutterstudy.api.config.security.AuthenticationUser;
import org.flutterstudy.api.model.dto.LoginRequest;
import org.flutterstudy.api.model.dto.RegisterFormData;
import org.flutterstudy.api.domain.user.enums.UserRole;
import org.flutterstudy.api.config.security.annotations.CurrentUser;
import org.flutterstudy.api.service.UserService;
import lombok.AllArgsConstructor;
import org.flutterstudy.api.service.cookie.JwtCookieHandler;
import org.flutterstudy.api.service.exception.NotFoundMatchedUser;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

	private JwtCookieHandler jwtCookieHandler;

	private UserService userService;

	@GetMapping(value = "/hello")
	ResponseEntity<String> hello(){
		return ResponseEntity.ok("Hello!!");
	}


	@PostMapping(value = "/register")
	ResponseEntity<User> register(RegisterFormData registerForm){

		User user = userService.register(registerForm);

		return ResponseEntity.ok(user);
	}

	@GetMapping("/")
	ResponseEntity<Model> home(@CurrentUser AuthenticationUser user, Model model) throws UnsupportedEncodingException {

		if (user != null) {
			model.addAttribute("user_info", user.getId());
			model.addAttribute("user_email", user.getUsername());
		}

		return ResponseEntity.ok(model);
	}

	@PutMapping(value = "/{userId}/role")
	ResponseEntity<User> appendRole(
			@PathVariable Long userId,
			@RequestParam(name = "roleName") UserRole role){

		User user = userService.appendRole(userId, role);
		return ResponseEntity.ok(user);
	}

	@GetMapping(value = "/email/exist")
	ResponseEntity<Map<String, Boolean>> isExistEmail(){
		Map<String, Boolean> result = new HashMap<>();
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/login")
	public String login(LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
		User user = null;
		try {
			user = userService.getMatchedUser(loginRequest);
		} catch (NotFoundMatchedUser e) {
			return "redirect:/login";
		}

		httpServletResponse.addCookie(jwtCookieHandler.make(user));
		return "redirect:/";
	}

	@GetMapping(value = "/logout")
	public String logout(HttpServletResponse httpServletResponse){
		httpServletResponse.addCookie(jwtCookieHandler.delete());
		return "redirect:/";
	}
}
