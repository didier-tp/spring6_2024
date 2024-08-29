package tp.appliSpringRennes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppliSpringRennesApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.profiles("dev"); //setting profiles here
         // or with system properties of the server (ex: tomcat)
		return builder.sources(AppliSpringRennesApplication.class);
	}

	public static void main(String[] args) {
		//System.setProperty("spring.profiles.default","dev");
		System.setProperty("spring.profiles.default","dev,withSecurity");
		SpringApplication.run(AppliSpringRennesApplication.class, args);
		System.out.println("http://localhost:8080/appliSpringRennes");
	}

}
