package tp.appliSpring.core.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import tp.appliSpring.AppliSpringApplication;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;


@SpringBootTest(classes= {AppliSpringApplication.class})//reprendre la configuration de la classe principale
@ActiveProfiles({  "dev" })

public class TestCompteDaoWithTransaction {
	
    private static Logger logger = LoggerFactory.getLogger(TestCompteDaoWithTransaction.class);
	
	@Autowired
	private DaoCompte daoCompte; //à tester
	
	@Autowired
	private DaoOperation daoOperation;
	
	@Autowired
    TransactionTemplate txTemplate;
	
	
	@Test
	public void testCompteAvecOperationsEtAvecTransactionDeNiveauTest() {
		System.out.println("testCompteAvecOperationsEtAvecTransactionDeNiveauTest");
		//phase1 (avec transaction1 comitée): insérer jeyx de données
		Long numCptA = txTemplate.execute(transactionStatus->{
	        	Compte cptA = daoCompte.save(new Compte(null,"compteAha",101.0));
	            Operation op1 = new Operation(null,"achat 1" , -5.0 , new Date());
	            op1.setCompte(cptA); daoOperation.save(op1);
	      		Operation op2 = new Operation(null,"achat 2" , -6.0 , new Date());
	      		op2.setCompte(cptA);daoOperation.save(op2);
	            return cptA.getNumero();
	        });
        System.out.println("numCptA="+numCptA);
        //phase2 (avec transaction2 comitée): relire les données stockées en base
		txTemplate.execute(transactionStatus->{
	        	Compte cptArelu = daoCompte.findById(numCptA).get();
	      		System.out.println("cptArelu="+cptArelu);
	      		//NB:ici en mode transactionnel , pas de lazy exception bien que LAZY et simple appel à findById
	      	    for(Operation op: cptArelu.getOperations()) {
	      	    	System.out.println("\t op="+op);
	      	    }  
	      	    assertTrue(cptArelu.getOperations().size()==2);
	           return null;
	        });
		}

}
