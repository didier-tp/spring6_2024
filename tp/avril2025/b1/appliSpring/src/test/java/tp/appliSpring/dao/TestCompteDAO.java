package tp.appliSpring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.entity.Compte;

@SpringBootTest //extension Spring pour Junit
//démarre un test en reprenant la configuration de la classe principale (celle où il y a main())
@ActiveProfiles("dev")//pour prendre en compte application-dev.properties
public class TestCompteDAO {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDAO.class);
	
	@Autowired
	private CompteDAO compteDAO;//composant à tester (pris en charge par spring)
	
	@Test
	public void testCrudCompte() {
		Compte cptA = new Compte(null,"compteA",50.0);
		Compte cptASauvegarde = compteDAO.save(cptA);//sauvegarder le compte en base de données
		logger.debug("cptASauvegarde="+cptASauvegarde);
		
		Compte cptARelu = compteDAO.findById(cptASauvegarde.getNumero()).get();
		logger.debug("cptARelu="+cptARelu);
		assertEquals("compteA",cptARelu.getLabel());
		
	}

}
