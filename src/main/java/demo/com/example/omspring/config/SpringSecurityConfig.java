package demo.com.example.omspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	/*
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	 * Exception { http.csrf().disable().authorizeHttpRequests(auth -> auth
	 * .requestMatchers("/users/register", "/users/login").permitAll()
	 * //.requestMatchers("/users/register", "/users/login").permitAll()
	 * .anyRequest().authenticated()).oauth2Login(withDefaults()); return
	 * http.build(); }
	 */
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/users/register", "/users/login").permitAll()
	            .anyRequest().authenticated()
	        )
	        .oauth2Login(oauth -> oauth.disable()) // Disable OAuth2 login redirect for API calls
	        .formLogin(form -> form.disable())     // Disable form login
	        .httpBasic();                          // Use HTTP Basic or JWT for auth
	    return http.build();
	}

}
