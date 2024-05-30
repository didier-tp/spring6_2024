package tp.appliSpring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
	
	@Bean
	protected SecurityFilterChain myFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(
				//exemple très permissif ici à grandement adapter !!!!
				auth -> auth.requestMatchers("/**").permitAll() );
		return http.build();
	}

}
