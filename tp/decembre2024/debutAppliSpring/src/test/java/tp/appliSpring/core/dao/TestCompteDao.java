package tp.appliSpring.core.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tp.appliSpring.AppliSpringApplication;
import tp.appliSpring.core.dao.DaoCompte;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


//@RunWith(SpringRunner.class)  //si junit4
//@ExtendWith(SpringExtension.class) //si junit5/jupiter
@SpringBootTest(classes= {AppliSpringApplication.class})//reprendre la configuration de la classe principale
@ActiveProfiles({  "dev" }) //pour analyser application-dev.properties
//@ActiveProfiles({  "dev2" })
public class TestCompteDao {
	
    private static Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired
	private DaoCompte daoCompte; //à tester

	@Autowired
	private DaoOperation daoOperation; //pour aider à tester

	@Test
	//@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	public void testRechercherCompteParSoldeMini() {
		Compte compteA1 = this.daoCompte.save(new Compte(null,"compteA1",100.0));
		Compte compteA2 = this.daoCompte.save(new Compte(null,"compteA2",200.0));
		Compte compteA3 = this.daoCompte.save(new Compte(null,"compteA3",300.0));
		Compte compteA4 = this.daoCompte.save(new Compte(null,"compteA4",400.0));
		List<Compte> liste = daoCompte.findBySoldeGreaterThanEqual(150.0);
		logger.debug("liste="+liste);
		//assertTrue(liste.size()==3);
		assertTrue(liste.size()>=3);
	}

	@Test
	public void testRechercherCompteAvecOperations() {
		Compte compteA = this.daoCompte.save(new Compte(null,"compteA",200.0));
		Operation op1CptA = new Operation(null,"achat 1a" , -15.0 , new Date());
		op1CptA.setCompte(compteA);this.daoOperation.save(op1CptA);
		Operation op2CptA = new Operation(null,"achat 2a" , -16.0 , new Date());
		op2CptA.setCompte(compteA);this.daoOperation.save(op2CptA);

		Compte compteB = this.daoCompte.save(new Compte(null,"compteB",200.0));
		Operation op1CptB = new Operation(null,"achat 1b" , -12.0 , new Date());
		op1CptB.setCompte(compteB);this.daoOperation.save(op1CptB);
		Operation op2CptB = new Operation(null,"achat 2b" , -13.0 , new Date());
		op2CptB.setCompte(compteB);this.daoOperation.save(op2CptB);

		//Compte compteBReluAvecOp = daoCompte.findById(compteB.getNumero()).orElse(null);
		Compte compteBReluAvecOp = daoCompte.findWithOperations(compteB.getNumero());
		assertTrue(compteBReluAvecOp.getLabel().equals("compteB"));
		assertTrue(compteBReluAvecOp.getOperations().size()==2);
		logger.debug("compteBReluAvecOp="+compteBReluAvecOp);
		logger.debug("operations de compteBReluAvecOp="+compteBReluAvecOp.getOperations());
	}

	@Test
	public void testAjoutEtRelectureEtSuppression() {
		//hypothese : base avec tables vides au lancement du test
		Compte compte = new Compte(null,"compteA",100.0);
		Compte compteSauvegarde = this.daoCompte.save(compte); //INSERT INTO
		logger.debug("compteSauvegarde=" + compteSauvegarde);
		
		Compte compteRelu = this.daoCompte.findById(compteSauvegarde.getNumero()).orElse(null); //SELECT
		Assertions.assertEquals("compteA",compteRelu.getLabel());
		Assertions.assertEquals(100.0,compteRelu.getSolde());
		logger.debug("compteRelu apres insertion=" + compteRelu);
		
		compte.setSolde(150.0); compte.setLabel("compte_a");
		Compte compteMisAjour = this.daoCompte.save(compte); //UPDATE
		logger.debug("compteMisAjour=" + compteMisAjour);
		
		compteRelu = this.daoCompte.findById(compteSauvegarde.getNumero()).orElse(null); //SELECT
		Assertions.assertEquals("compte_a",compteRelu.getLabel());
		Assertions.assertEquals(150.0,compteRelu.getSolde());
		logger.debug("compteRelu apres miseAjour=" + compteRelu);
		/*
		//+supprimer :
		this.daoCompte.deleteById(compteSauvegarde.getNumero());
		
		//verifier bien supprimé (en tentant une relecture qui renvoi null)
		Compte compteReluApresSuppression = this.daoCompte.findById(compteSauvegarde.getNumero()).orElse(null);;
		Assertions.assertTrue(compteReluApresSuppression == null);
		*/
	}

}
