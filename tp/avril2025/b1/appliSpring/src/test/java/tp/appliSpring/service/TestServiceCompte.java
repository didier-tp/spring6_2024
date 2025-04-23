package tp.appliSpring.service;

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
public class TestServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(TestServiceCompte.class);
	
	@Autowired
	private ServiceCompte serviceCompte; //chose à tester

	@Test
	public void testCompte() {
		Compte cA = serviceCompte.insertCompte(new Compte(null,"cA",100.0));
		logger.debug("cA=" + cA);
		Compte cARelu = serviceCompte.searchByNumero(cA.getNumero());
		logger.debug("cARelu=" + cARelu);
		assertEquals("cA",cARelu.getLabel());
	}
}
