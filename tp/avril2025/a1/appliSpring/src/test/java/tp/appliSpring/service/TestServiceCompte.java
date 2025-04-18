package tp.appliSpring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.entity.Compte;


@SpringBootTest()
@ActiveProfiles("mysql")
//@ActiveProfiles("postgres")
public class TestServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(TestServiceCompte.class);
	
	@Autowired
	private ServiceCompte serviceCompte;//composant à tester
	
	@Test
	public void testService() {
		Compte cA = new Compte(null,"compteA",50.0);//nouveau compte pas encore sauvegardé
		cA /* compte sauvegardé */= serviceCompte.insertCompte(cA);
		logger.debug(cA.toString());
		
		Compte cARelu = serviceCompte.searchById(cA.getNumero());
		assertEquals("compteA",cARelu.getLabel());
		assertEquals(50.0,cARelu.getSolde(),0.000001);
		logger.debug(cARelu.toString());
	}

}
