package tp.appliSpring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest()
//par defaut, @SpringBootTest va utiliser la config automatique de springBoot
//et le fichier application.properties

//pour tenir compte en plus de application-mysql.properties il faut ajouter @ActiveProfile
@ActiveProfiles
public class TestCompteDao {
	
	@Test
	public void testDao() {
		System.out.println("testDao");
	}

}
