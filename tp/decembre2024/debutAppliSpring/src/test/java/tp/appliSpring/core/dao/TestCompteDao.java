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
	public void testRechercherCompteParSoldeMini() {
		//on ajoute plein de s avec des soldes diférents

		//on recherche les comptes dont le solde est superieur au minimum = 0 (ou 50.0)

		//on verifie qu'on a les bons
	}

	@Test
	public void testRechercherCompteAvecOperations() {
		//on ajoute plein de comptes avec des operations rattachées

		//on recherche un de ces comptes selon un numero precis

		//on verifie qu'on a recuperé le bon compte AVEC les bonnes operations rattachées:
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
