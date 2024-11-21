package tp.appliSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EntityScan("tp.appliSpring.core.entity") //not required here because tp.appliSpring.core.entity is a subpart of tp.appliSpring (default ComponentScan)
public class AppliSpringApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		//return super.configure(builder);
		builder.profiles("dev","reInit");
		// or with system properties of the server (ex: tomcat)
		return builder.sources(AppliSpringApplication.class);
	}

	public static void initProfiles() {
		//java .... -Dspring.profiles.active=reInit,dev
		String profilsActifs  = System.getProperty("spring.profiles.active");
		if(profilsActifs==null || profilsActifs.equals("")) {
			//valeur par défaut si pas encore précisé
			//System.setProperty("spring.profiles.default", "dev,reInit");
			System.setProperty("spring.profiles.default", "dev,reInit,withSecurity");
			//System.setProperty("spring.profiles.default", "DEV"); //avec H2 et reInit
			//System.setProperty("spring.profiles.default", "DEV2"); //avec mysql et reInit
			//System.setProperty("spring.profiles.active", "dev2,reInit");
			//System.setProperty("spring.profiles.active", "prod");
		}
	}

	public static void main(String[] args) {
		initProfiles();
		SpringApplication.run(AppliSpringApplication.class, args);
		//url de l'appli
		System.out.println("http://localhost:8181/appliSpring");
	}

}
