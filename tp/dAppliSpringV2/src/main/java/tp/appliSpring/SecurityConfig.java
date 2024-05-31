package tp.appliSpring;

import org.mycontrib.mysecurity.jwt.util.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("withSecurity")
@ComponentScan(basePackages = {"org.mycontrib.mysecurity"})
@EnableMethodSecurity()//pour que le test @PreAuthorize("hasRole('ADMIN')") puisse bien fonctionner
public class SecurityConfig {

	@Bean
	@Order(1)
	protected SecurityFilterChain restFilterChain(HttpSecurity http,JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		return http.securityMatcher("/rest/**")
		    .authorizeHttpRequests(
				auth -> auth.requestMatchers("/rest/api-login/public/login").permitAll()
				            .requestMatchers(HttpMethod.GET,"/rest/api-bank/compte/**").permitAll()
				            //.requestMatchers(HttpMethod.DELETE,"/rest/api-bank/compte/**").hasRole("ADMIN") ou bien @PreAuthorize("hasRole('ADMIN')") dans la classe CompteRestCtrl
				            .requestMatchers("/rest/**").authenticated()
				)
		    .cors( Customizer.withDefaults())
		    
		  //enable CORS (avec @CrossOrigin sur class @RestController)
		  .csrf( csrf -> csrf.disable() )
		  // If the user is not authenticated, returns 401
		  .exceptionHandling(eh ->  eh.authenticationEntryPoint(getRestAuthenticationEntryPoint() ))
			
		  // This is a stateless application, disable sessions
		  .sessionManagement(sM -> sM.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		
		  // Custom filter for authenticating users using tokens
		  .addFilterBefore(jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class)
		  .build();
	}
	
	private AuthenticationEntryPoint getRestAuthenticationEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}
	
	@Bean
	@Order(3)
	protected SecurityFilterChain otherFilterChain(HttpSecurity http,PasswordEncoder passwordEncoder) throws Exception {
		return http.securityMatcher("/**")
		     .authorizeHttpRequests(
				// pour image , html , css , js
				auth -> auth.requestMatchers("/**").permitAll())
		     .build();
	}
	
	@Bean @Qualifier("rest")
	AuthenticationManager authManager(HttpSecurity http,PasswordEncoder passwordEncoder)  throws Exception{
		AuthenticationManagerBuilder authenticationManagerBuilder =
				authenticationManagerBuilderFromHttpSecurity(http);
		
		authenticationManagerBuilder.inMemoryAuthentication()
		.withUser("user1").password(passwordEncoder.encode("pwd1")).roles("USER").and()
		.withUser("admin1").password(passwordEncoder.encode("pwd1")).roles("ADMIN").and()
		.withUser("user2").password(passwordEncoder.encode("pwd2")).roles("USER").and()
		.withUser("admin2").password(passwordEncoder.encode("pwd2")).roles("ADMIN");
		
		AuthenticationManager authManager = authenticationManagerBuilder.build();
		//http.authenticationManager(authManager);
		return authManager;
		
	}
	
	
  /*
   //deja dans sous projet common-security
	@Bean
	public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
	//or new BCryptPasswordEncoder(int strength) with strength between 4 and 31
	}
   */

	public static AuthenticationManagerBuilder authenticationManagerBuilderFromHttpSecurity(HttpSecurity httpSecurity) {
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.parentAuthenticationManager(null);
		return authenticationManagerBuilder;
	}

}
