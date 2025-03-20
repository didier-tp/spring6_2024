package tp.appliSpring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tp.appliSpring.security.generic.standalone.JwtAuthenticationFilter;
//import xy.MySecurityConfig;

@Configuration
@Profile("withSecurity")
//@Import(MySecurityConfig.class)//import nécessaire si dans package xy plutot que tp.appliSpring
//plus besoin de @Import explicite si auto-configuration dans sous projet mysecurity-autoconfigure
@EnableMethodSecurity()//pour que le test @PreAuthorize("hasRole('ADMIN')") puisse bien fonctionner
public class SecurityConfigForRest {
/*
	//optional config from mysecurity-autoconfigure
	@Autowired(required = false)
	@Qualifier("permitAllListAsString")
	private String permitAllListAsString;

	@Autowired(required = false)
	@Qualifier("permitGetListAsString")
	private String permitGetListAsString;

	@Autowired(required = false)
	@Qualifier("authListAsString")
	private String authListAsString;

	@Autowired(required = false)
	private MyPermissionConfigurer myPermissionConfigurer;
*/

	@Bean
	@Order(1)
	@Profile("!withoutOAuth2")
	public SecurityFilterChain restFilterChainWithOAuth2(HttpSecurity http) throws Exception {
		return restFilterChainBuilder(http)
				.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
				//with spring.security.oauth2.resourceserver.jwt.issuer-uri=https://www.d-defrance.fr/keycloak/realms/sandboxrealm
				.build();
	}

/*
	// VERSION AVEC MyPermissionConfigurer et propriétés de type mysecurity.area.permit-all-list,mysecurity.area.permit-get-list,mysecurity.area.auth-list
	private HttpSecurity restFilterChainBuilder(HttpSecurity http) throws Exception {
		System.out.println("permitAllListAsString=" + permitAllListAsString);//simple affichage
		System.out.println("permitGetListAsString=" + permitGetListAsString);//simple affichage
		System.out.println("authListAsString=" + authListAsString);//simple affichage
		//et on se sert indirectement de ces valeurs pour configurer la  securité via  myPermissionConfigurer.addPermissionsFromAreaConfig()

		return http.securityMatcher("/rest/**")
				.authorizeHttpRequests(
						auth -> myPermissionConfigurer.addPermissionsFromAreaConfig(auth)
				)
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable()) //important to get 401/Unauthorized
				.sessionManagement(sM ->
						sM.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //important to get 401/Unauthorized
	}
*/


// VERSION SANS MyPermissionConfigurer
	private HttpSecurity restFilterChainBuilder(HttpSecurity http) throws Exception {

		return http.securityMatcher("/rest/**")
				.authorizeHttpRequests(
						auth -> auth
								.requestMatchers(HttpMethod.GET, "/rest/api-bank/v1/comptes/**").permitAll()
								.requestMatchers("/rest/api-auth/v1/standalone-jwt-auth").permitAll()
								//.requestMatchers(HttpMethod.DELETE,"/rest/api-bank/comptes/**").hasRole("ADMIN") ou bien @PreAuthorize("hasRole('ADMIN')") dans la classe CompteRestCtrl
								.requestMatchers("/rest/**").authenticated()
				)
				.cors(Customizer.withDefaults())
				.csrf(csrf -> csrf.disable()) //important to get 401/Unauthorized
				.sessionManagement(sM ->
						sM.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //important to get 401/Unauthorized

	}


	//############## JUST FOR withoutOAuth2 (plan B si pas de OAuth2)  #####################
    ///rest/api-auth/v1/standalone-jwt-auth
	//in tp.appliSpring.security.generic.standalone.rest.LoginRestCtrl


	//NB: MyUserDetailsService is in tp.appliSpring.bank.security
	@Autowired(required = false) @Qualifier("local")
	private UserDetailsService userDetailsServiceLocal;


	@Bean
	@Order(1)
	@Profile("withoutOAuth2")
	public SecurityFilterChain restFilterChainWithoutOAuth2(HttpSecurity http) throws Exception {
		return restFilterChainBuilder(http)
				.userDetailsService(userDetailsServiceLocal)
				.exceptionHandling(eh -> eh.authenticationEntryPoint(getRestAuthenticationEntryPoint())
				    /*eH -> eH.defaultAuthenticationEntryPointFor(
						getRestAuthenticationEntryPoint(), new AntPathRequestMatcher("/rest/**")
				    )*/)
				.addFilterBefore(jwtAuthenticationFilter,  UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Autowired(required = false) //exists or not (selon profiles)
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired(required = false)
	private PasswordEncoder passwordEncoder;

	private AuthenticationEntryPoint getRestAuthenticationEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}

	@Bean
	@Profile("withoutOAuth2")
	public AuthenticationProvider authenticationProviderwithoutOAuth2(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsServiceLocal);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	@Profile("withoutOAuth2")
	public AuthenticationManager authenticationManagerWithoutOAuth2(AuthenticationConfiguration authConfig)
		throws Exception {
		return authConfig.getAuthenticationManager();
	}

}
