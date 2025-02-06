package tp.appliSpring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AppliSpringApplication {
	
	private static Logger logger = LoggerFactory.getLogger(AppliSpringApplication.class);
	
	
	public static void initProfiles() {
		//java .... -Dspring.profiles.active=reInit,dev
		String profilsActifs  = System.getProperty("spring.profiles.active");
		if(profilsActifs!=null) {
			System.out.println("spring.profiles.active="+profilsActifs);
		}else {
			//String defaultProfils  = "dev,reInit";
			String defaultProfils  = "dev,reInit,withSecurity";
			//String defaultProfils  = "dev2,reInit";
			//String defaultProfils  = "prod";
			System.setProperty("spring.profiles.default", defaultProfils);
			System.out.println("spring.profiles.default="+defaultProfils);
		}
	}

	public static void main(String[] args) {
		initProfiles();
		SpringApplication.run(AppliSpringApplication.class, args);
		//url de l'appli
		//System.out.println("http://localhost:8181/appliSpring");
		logger.info("http://localhost:8181/appliSpring");
	}

}
