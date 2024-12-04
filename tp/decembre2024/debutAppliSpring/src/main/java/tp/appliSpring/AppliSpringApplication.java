package tp.appliSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppliSpringApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.profiles("dev,reInit");  //setting profiles here
        // or with system properties of the server (ex: tomcat)
		return builder.sources(AppliSpringApplication.class);
	}

	public static void initProfiles() {
		//java .... -Dspring.profiles.active=reInit,dev
		String profilsActifs  = System.getProperty("spring.profiles.active");
		if(profilsActifs==null || profilsActifs.equals("")) {
			//valeur par défaut si pas encore précisé
			System.setProperty("spring.profiles.active", "dev,reInit");
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
