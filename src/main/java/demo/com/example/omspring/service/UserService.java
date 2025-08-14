package demo.com.example.omspring.service;

import java.util.Collections;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import demo.com.example.omspring.model.UserModel;
import demo.com.example.omspring.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final String serverUrl = "http://localhost:8082";
	private final String realm = "master";
	private final String clientId = "admin-cli";
	private final String adminUsername = "admin"; // Keycloak admin
	private final String adminPassword = "admin@123"; // Keycloak admin password

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private Keycloak getKeycloakInstance() {
		return KeycloakBuilder.builder().serverUrl(serverUrl).realm("master") // admin realm
				.grantType(OAuth2Constants.PASSWORD).clientId(clientId).username(adminUsername).password(adminPassword)
				.build();
	}

	@Transactional
	public UserModel registerUser(String username, String email, String firstName, String lastName, String password) {
		Keycloak keycloak = getKeycloakInstance();

		// Create password credentials
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setTemporary(false);
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);

		// Create User in Keycloak
		UserRepresentation user = new UserRepresentation();
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setCredentials(Collections.singletonList(credential));

		var response = keycloak.realm(realm).users().create(user);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed to create user in Keycloak. Status: " + response.getStatus());
		}

		// Get Keycloak userId
		String locationHeader = response.getLocation().getPath();
		String keycloakUserId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);

		// Save to DB
		UserModel savedUser = new UserModel(null, keycloakUserId, username, email, firstName, lastName);
		return userRepository.save(savedUser);
	}
}
