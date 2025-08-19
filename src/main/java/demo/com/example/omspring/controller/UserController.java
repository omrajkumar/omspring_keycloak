package demo.com.example.omspring.controller;

import java.util.Map;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sun.istack.NotNull;

import demo.com.example.omspring.model.AuthRequest;
import demo.com.example.omspring.model.UserModel;
import demo.com.example.omspring.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Value("${keycloak.auth-server-url}")
	private String keycloakBaseUrl;

	@Value("${keycloak.realm}")
	private String realm;

	@Value("${keycloak.resource}")
	private String clientId;

	@Value("${keycloak.credentials.secret}")
	private String clientSecret;

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

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {

		String username = loginRequest.get("username");// loginRequest.getUsername();
		String password = loginRequest.get("password");// loginRequest.getPassword();

		String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String body = "client_id=" + clientId + "&client_secret=" + clientSecret + "&username=" + username
				+ "&password=" + password + "&grant_type=password";

		HttpEntity<String> entity = new HttpEntity<>(body, headers);

		try {
			ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, Map.class);
			return ResponseEntity.ok(response.getBody());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
		}
	}

}
