package demo.com.example.omspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "keycloak_users")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String keycloakUserId; // store KC ID for reference
	private String username;
	private String email;
	private String firstName;
	private String lastName;

	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserModel(Long id, String keycloakUserId, String username, String email, String firstName, String lastName) {
		super();
		this.id = id;
		this.keycloakUserId = keycloakUserId;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeycloakUserId() {
		return keycloakUserId;
	}

	public void setKeycloakUserId(String keycloakUserId) {
		this.keycloakUserId = keycloakUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", keycloakUserId=" + keycloakUserId + ", username=" + username + ", email="
				+ email + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
