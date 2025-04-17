package tp.appliSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppliSpringApplication {

	public static void main(String[] args) {
		//activation d'un profile (variante de configuration)
		//au démarrage de l'application
		System.setProperty("spring.profiles.active", "mysql");
		//System.setProperty("spring.profiles.active", "mysql,profileComplementaire2");
		SpringApplication.run(AppliSpringApplication.class, args);
		System.out.println("http://localhost:8181/appliSpring");
	}

}
