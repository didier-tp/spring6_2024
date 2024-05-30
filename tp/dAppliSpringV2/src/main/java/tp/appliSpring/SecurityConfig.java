package tp.appliSpring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@ComponentScan(basePackages = {"org.mycontrib.mysecurity"})
public class SecurityConfig {

	@Bean
	protected SecurityFilterChain myFilterChain(HttpSecurity http,PasswordEncoder passwordEncoder) throws Exception {
		http.authorizeHttpRequests(
				// exemple très permissif ici à grandement adapter !!!!
				auth -> auth.requestMatchers("/**").permitAll());
		
		return http.build();
	}
	
	@Bean 
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
