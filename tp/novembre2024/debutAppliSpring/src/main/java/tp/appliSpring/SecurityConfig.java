package tp.appliSpring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("withSecurity")
@EnableMethodSecurity()//pour que le test @PreAuthorize("hasRole('ADMIN')") puisse bien fonctionner
public class SecurityConfig {

	@Bean
	@Order(1)
	protected SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/rest/**")
		    .authorizeHttpRequests(
				auth -> auth/*.requestMatchers("/rest/api-login/public/login").permitAll()*/
				            .requestMatchers(HttpMethod.GET,"/rest/api-bank/comptes/**").permitAll()
				            //.requestMatchers(HttpMethod.DELETE,"/rest/api-bank/comptes/**").hasRole("ADMIN") ou bien @PreAuthorize("hasRole('ADMIN')") dans la classe CompteRestCtrl
				            .requestMatchers("/rest/**").authenticated()
				)
		    .cors( Customizer.withDefaults())
		    .sessionManagement(sM ->
		    		sM.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
		    //with spring.security.oauth2.resourceserver.jwt.issuer-uri=https://www.d-defrance.fr/keycloak/realms/sandboxrealm
		  .build();
	}
	
		
	@Bean
	@Order(3)
	protected SecurityFilterChain otherFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/**")
		     .authorizeHttpRequests(
				// pour image , html , css , js
				auth -> auth.requestMatchers("/**").permitAll())
		     .build();
	}
	

}
