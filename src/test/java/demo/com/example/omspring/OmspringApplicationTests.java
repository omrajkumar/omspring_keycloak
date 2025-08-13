package demo.com.example.omspring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
	    "spring.security.oauth2.client.registration.keycloak.client-id=test-client",
	    "spring.security.oauth2.client.registration.keycloak.client-secret=test-secret",
	    "spring.security.oauth2.client.provider.keycloak.issuer-uri=http://localhost:8080/realms/test"
	})
class OmspringApplicationTests {

	@Test
	void contextLoads() {
	}

}
