package demo.com.example.omspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.com.example.omspring.model.UserModel;
import demo.com.example.omspring.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public UserModel register(@RequestParam String username, @RequestParam String email, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String password) {
		return userService.registerUser(username, email, firstName, lastName, password);
	}

	@GetMapping("/")
	public String home() {
		return "Welcome! <a href='/oauth2/authorization/keycloak'>Login with Keycloak</a>";
	}

	@GetMapping("/secure")
	public String secure() {
		return "You are logged in!";
	}
}
