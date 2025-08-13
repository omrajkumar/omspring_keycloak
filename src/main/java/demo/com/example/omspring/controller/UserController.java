package demo.com.example.omspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/")
	public String Home() {
		return "Hello! this is a public page!";
	}

	@GetMapping("/private")
	public String secured() {
		return "Hello ! this is a private page !";
	}
}
