package tp.appliSpring.dao;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.entity.Compte;


@SpringBootTest()
//par defaut, @SpringBootTest va utiliser la config automatique de springBoot
//et le fichier application.properties

//pour tenir compte en plus de application-mysql.properties il faut ajouter @ActiveProfile
@ActiveProfiles("mysql")
public class TestCompteDao {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired
	private CompteDao compteDao;//composant à tester
	
	@Test
	public void testDao() {
		Compte cA = new Compte(null,"commpteA",50.0);//nouveau compte pas encore sauvegardé
		cA /* compte sauvegardé */= compteDao.save(cA);
		logger.debug(cA.toString());
	}

}
