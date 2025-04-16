package tp.appliSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppliSpringApplication {

	public static void main(String[] args) {
		
		//choix d'éventuel profiles  activés au démarrage:
		//pour ici demander à prendre en compte application-dev.properties
		//en plus de application.properties
		System.setProperty("spring.profiles.active","dev");
		//System.setProperty("spring.profiles.active","prod"); //variante de conf en prod
		
		SpringApplication.run(AppliSpringApplication.class, args);
		System.out.println("http://localhost:8181/appliSpring");
	}

}
