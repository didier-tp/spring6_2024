package tp.appliSpring.core.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tp.appliSpring.AppliSpringApplication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AppliSpringApplication.class})
@ActiveProfiles({"dev"})
public class TestDaoCompte {

    private static Logger logger = LoggerFactory.getLogger(TestDaoCompte.class);

    @Autowired
    private DaoCompte daoCompte; //à tester

    @Autowired
    private DaoOperation daoOperation; //pour aider à tester

    @Test
    public void testFindBySoldeMini(){
        this.daoCompte.save(new Compte(null,"compteA1",100.0));
        this.daoCompte.save(new Compte(null,"compteA2",300.0));
        this.daoCompte.save(new Compte(null,"compteA3",500.0));
        this.daoCompte.save(new Compte(null,"compteA4",700.0));

        List<Compte> comptes =daoCompte.findBySoldeGreaterThanEqual(500.0);
        assertTrue(comptes.size()>=2);
        logger.debug("compte avec solde>=500 : " + comptes.toString());
    }


    @Test
    public void testFindWithOperations(){
        Compte cB1 = this.daoCompte.save(new Compte(null,"compteB1",100.0));
        Operation op1cB1 = new Operation(null,"achat bonbon" , -4.50 , new Date());
        op1cB1.setCompte(cB1);  this.daoOperation.save(op1cB1);
        Operation op2cB1 = new Operation(null,"achat gateau" , -8.50 , new Date());
        op2cB1.setCompte(cB1);  this.daoOperation.save(op2cB1);

        Compte cB2 = this.daoCompte.save(new Compte(null,"compteB2",300.0));
        Operation op1cB2 = new Operation(null,"achat boisson" , -6.50 , new Date());
        op1cB2.setCompte(cB2);  this.daoOperation.save(op1cB2);

        Compte compteReluAvecOperations =daoCompte.findWithOperations(cB1.getNumero());
        assertTrue(compteReluAvecOperations.getOperations().size()==2);
        logger.debug("compteReluAvecOperations = " + compteReluAvecOperations);
        for(Operation op : compteReluAvecOperations.getOperations()){
            logger.debug("\t " + op.toString());
        }
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
		Compte compteReluApresSuppression = this.daoCompte.findById(compteSauvegarde.getNumero());
		Assertions.assertTrue(compteReluApresSuppression == null);
		*/
    }

}
