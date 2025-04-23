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
	public void testBonTransfert() {
		Compte cx = serviceCompte.insertCompte(new Compte(null,"cx",100.0));
		Compte cy = serviceCompte.insertCompte(new Compte(null,"cy",150.0));
		logger.debug("avant bon virement, cx:" + cx.getSolde() + " cy:" + cy.getSolde());
		serviceCompte.transferer(10.0, cx.getNumero(), cy.getNumero());
		Compte cxApres = serviceCompte.searchByNumero(cx.getNumero());
		Compte cyApres = serviceCompte.searchByNumero(cy.getNumero());
		logger.debug("apres bon virement, cx:" + cxApres.getSolde() + " cy:" + cyApres.getSolde());
		assertEquals(cx.getSolde()-10, cxApres.getSolde(),0.00001);
		assertEquals(cy.getSolde()+10, cyApres.getSolde(),0.00001);
	}
	
	@Test
	public void testCompte() {
		Compte cA = serviceCompte.insertCompte(new Compte(null,"cA",100.0));
		logger.debug("cA=" + cA);
		Compte cARelu = serviceCompte.searchByNumero(cA.getNumero());
		logger.debug("cARelu=" + cARelu);
		assertEquals("cA",cARelu.getLabel());
	}
}
