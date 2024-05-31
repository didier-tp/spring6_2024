package tp.appliSpring;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("!withSecurity")
public class WithoutSecurityConfig {

	@Bean
	protected SecurityFilterChain withoutSecurityFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/**")
		     .authorizeHttpRequests(
				// pour image , html , css , js , et tout le reste
				auth -> auth.requestMatchers("/**").permitAll())
		     .cors( Customizer.withDefaults())
	    
	  //enable CORS (avec @CrossOrigin sur class @RestController)
	  .csrf( csrf -> csrf.disable() )
	  // If the user is not authenticated, returns 401
	  .exceptionHandling(eh ->  eh.authenticationEntryPoint(getRestAuthenticationEntryPoint() ))
	  // This is a stateless application, disable sessions
	  .sessionManagement(sM -> sM.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	  .build();
	}

	private AuthenticationEntryPoint getRestAuthenticationEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}

	
	@Bean @Qualifier("rest")
	AuthenticationManager authManager(HttpSecurity http,PasswordEncoder passwordEncoder)  throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder =
				authenticationManagerBuilderFromHttpSecurity(http);
		
		authenticationManagerBuilder.inMemoryAuthentication()
		.withUser("user").password(passwordEncoder.encode("pwd")).roles("USER","ADMIN");
		
		AuthenticationManager authManager = authenticationManagerBuilder.build();
		//http.authenticationManager(authManager);
		return authManager;
		
	}
	
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		//or new BCryptPasswordEncoder(int strength) with strength between 4 and 31
		}
	 
		public static AuthenticationManagerBuilder authenticationManagerBuilderFromHttpSecurity(HttpSecurity httpSecurity) {
			AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
					.getSharedObject(AuthenticationManagerBuilder.class);
			authenticationManagerBuilder.parentAuthenticationManager(null);
			return authenticationManagerBuilder;
		}
	
}
