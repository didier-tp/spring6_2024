package tp.appliSpring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;


@SpringBootTest()
@ActiveProfiles("mysql")
//@ActiveProfiles("postgres")
public class TestServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(TestServiceCompte.class);
	
	@Autowired
	private ServiceCompte serviceCompte;//composant à tester
	
	
	@Test
	public void testBonVirement() {
		Compte c1 = serviceCompte.insertCompte(new Compte(null,"compteC1",50.0));
		Compte c2 = serviceCompte.insertCompte(new Compte(null,"compteC2",40.0));
		logger.debug("avant virement c1=" + c1.getSolde() +  " c2=" + c2.getSolde());
		serviceCompte.transferer(5.0, c1.getNumero(), c2.getNumero());
		Compte c1Apres = serviceCompte.searchById(c1.getNumero());
		Compte c2Apres = serviceCompte.searchById(c2.getNumero());
		logger.debug("apres virement c1=" + c1Apres.getSolde() +  " c2=" + c2Apres.getSolde());
		assertEquals(c1Apres.getSolde(),c1.getSolde()-5 ,0.000001);
		assertEquals(c2Apres.getSolde(),c2.getSolde()+5 ,0.000001);
	}
	
	
	@Test
	public void testMauvaisVirement() {
		Compte c1 = serviceCompte.insertCompte(new Compte(null,"compteC1",50.0));
		Compte c2 = serviceCompte.insertCompte(new Compte(null,"compteC2",40.0));
		logger.debug("avant mauvais virement c1=" + c1.getSolde() +  " c2=" + c2.getSolde());
		try {
			serviceCompte.transferer(5.0, c1.getNumero(), - c2.getNumero());
			//erreur volontaire (dans le test) sur numCptCred = -2
		} catch (BankException e) {
			logger.info("exception normale: " + e.getMessage());
		}
		Compte c1Apres = serviceCompte.searchById(c1.getNumero());
		Compte c2Apres = serviceCompte.searchById(c2.getNumero());
		logger.debug("apres mauvais virement c1=" + c1Apres.getSolde() +  " c2=" + c2Apres.getSolde());
		assertEquals(c1Apres.getSolde(),c1.getSolde() ,0.000001);
		assertEquals(c2Apres.getSolde(),c2.getSolde() ,0.000001);
	}
	
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
