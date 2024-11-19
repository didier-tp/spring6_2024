package tp.appliSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
//@EntityScan("tp.appliSpring.core.entity") //not required here because tp.appliSpring.core.entity is a subpart of tp.appliSpring (default ComponentScan)
public class AppliSpringApplication {
	
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
