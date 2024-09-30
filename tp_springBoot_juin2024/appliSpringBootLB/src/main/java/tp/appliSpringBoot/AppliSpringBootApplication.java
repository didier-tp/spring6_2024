package tp.appliSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppliSpringBootApplication {

	public static void main(String[] args) {
       // String mesProfils ="mysql,reinit";
		//String mesProfils ="h2,reinit";
		//String mesProfils ="h2";
		String mesProfils ="mysql";
		//System.setProperty("spring.profiles.active",mesProfils);
		System.setProperty("spring.profiles.default",mesProfils);
		SpringApplication.run(AppliSpringBootApplication.class, args);
		System.out.println("http://localhost:8080/appliSpringBoot");
	}

}
