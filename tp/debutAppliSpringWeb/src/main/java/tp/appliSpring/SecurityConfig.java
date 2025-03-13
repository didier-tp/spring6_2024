package tp.appliSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
//import xy.MySecurityConfig;

@Configuration
@Profile("withSecurity")
//@Import(MySecurityConfig.class)//import nécessaire si dans package xy plutot que tp.appliSpring
//plus besoin de @Import explicite si auto-configuration dans sous projet mysecurity-autoconfigure
@EnableMethodSecurity()//pour que le test @PreAuthorize("hasRole('ADMIN')") puisse bien fonctionner
public class SecurityConfig {
/*
	@Autowired(required = false)
	@Qualifier("permitListAsString")
	private String permitListAsString;
 */

	/*
	@Bean
	@Order(1)
	protected SecurityFilterChain restFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/rest/**")
		    .authorizeHttpRequests(
				auth -> auth.requestMatchers(HttpMethod.GET,"/......").permitAll()
				            .requestMatchers("/.....").authenticated()
				)
		    .cors( Customizer.withDefaults())
			.csrf( csrf -> csrf.disable() ) //important to get 401/Unauthorized
		    .sessionManagement(sM ->
		    		sM.sessionCreationPolicy(.......))  //important to get 401/Unauthorized
		    .oauth2ResourceServer(.....)
		    //with spring.security.oauth2.resourceserver.jwt.issuer-uri=https://www.d-defrance.fr/keycloak/realms/sandboxrealm
		  .build();
	}
	*/


	//NB: MyUserDetailsService is in tp.appliSpring.bank.site.security
	@Autowired
	@Qualifier("site")
	private UserDetailsService userDetailsServiceForSite;

	@Bean
	@Order(2)
	protected SecurityFilterChain siteFilterChain(HttpSecurity http) throws Exception {

		return http.securityMatcher("/site/**")
				.userDetailsService(userDetailsServiceForSite)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/site/app/**").permitAll()
								.requestMatchers("/site/basic/**").permitAll()
								/*.requestMatchers("/site/bank/**").authenticated()*/
								.requestMatchers("/site/bank/**").hasRole("CUSTOMER")
				)
				.csrf( Customizer.withDefaults() )

				//.formLogin( formLogin -> formLogin.permitAll() )
				.formLogin( formLogin -> formLogin.loginPage("/site/app/login")
						.failureUrl("/site/app/login-error")
						.defaultSuccessUrl("/site/app/toWelcome", false)
						.permitAll())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
				.build();
		//NB: /site/app/login et /site/app/login-error redigirent tous les deux vers templates/login.html
	}

	@Bean
	@Order(3)
	protected SecurityFilterChain otherFilterChain(HttpSecurity http) throws Exception {
		return http.securityMatcher("/**")
				.authorizeHttpRequests(
						// pour image , html , css , js
						auth -> auth.requestMatchers("/**").permitAll()
								.requestMatchers("/h2-console/**").permitAll()
				).cors( Customizer.withDefaults() )
				.csrf( csrf -> csrf.disable() )
				.headers(headers -> headers.frameOptions().sameOrigin())//pour h2-console
				.build();
	}
	

}
