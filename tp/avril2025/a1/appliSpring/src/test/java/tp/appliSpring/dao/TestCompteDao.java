package tp.appliSpring.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest()
//par defaut, @SpringBootTest va utiliser la config automatique de springBoot
//et le fichier application.properties

//pour tenir compte en plus de application-mysql.properties il faut ajouter @ActiveProfile
@ActiveProfiles("mysql")
public class TestCompteDao {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Test
	public void testDao() {
		logger.debug("testDao");
	}

}
