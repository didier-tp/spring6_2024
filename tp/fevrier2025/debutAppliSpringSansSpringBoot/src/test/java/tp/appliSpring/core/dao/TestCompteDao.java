package tp.appliSpring.core.dao;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tp.appliSpring.core.MySpringApplication;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;


//@RunWith(SpringRunner.class)  //si junit4
@ExtendWith(SpringExtension.class) //si junit5/jupiter
@ContextConfiguration(classes= {MySpringApplication.class})//reprendre la configuration de la classe principale
//@ActiveProfiles({ "embeddedDB" , "dev" })
public class TestCompteDao {
	
    private static Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired
	//@Qualifier("simu")
	//@Qualifier("jdbc")
	@Qualifier("jpa")
	private DaoCompte daoCompte; //à tester
	
	@Autowired @Qualifier("jpa")
	private DaoOperation daoOperation;
	
	@Test
	public void testCompteAvecOperations() {
		Compte compteCSauvegarde = this.daoCompte.save( new Compte(null,"compteC",100.0)); //INSERT INTO
		logger.debug("compteCSauvegarde=" + compteCSauvegarde);
		
		Operation op1 = new Operation(null,"achat 1",-50.0 , new Date());
		op1.setCompte(compteCSauvegarde);
		daoOperation.save(op1);
		
		Operation op2 = new Operation(null,"achat 2",-60.0 , new Date());
		op2.setCompte(compteCSauvegarde);
		daoOperation.save(op2);
		
		//Compte compteRelu = this.daoCompte.findById(compteCSauvegarde.getNumero()); //bug au niveau for() en mode lazy
		Compte compteRelu = this.daoCompte.findWithOperations(compteCSauvegarde.getNumero());//ok grace au mot clef FETCH
		logger.debug("compteRelu apres insertion=" + compteRelu);
		for(Operation op : compteRelu.getOperations()) {
			logger.debug("\t op" + op);
		}
		
		
	}

}
