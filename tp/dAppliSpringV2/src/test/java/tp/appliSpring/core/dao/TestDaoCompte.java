package tp.appliSpring.core.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.AppliSpringApplication;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;

@SpringBootTest(classes= {AppliSpringApplication.class})//reprendre la configuration de la classe principale
@ActiveProfiles({ "dev2"  })
public class TestDaoCompte {
	
	private static Logger logger = LoggerFactory.getLogger(TestDaoCompte.class);
	
	@Autowired
	private DaoCompte daoCompte; //à tester
	
	@Autowired
	private DaoOperation daoOperation; //pour aider à tester

	public TestDaoCompte() {
	}
	
	@Test
	public void testFindBySoldeGreaterThanEqual(){
		daoCompte.save(new Compte(null,"compteA1",50.0));
		daoCompte.save(new Compte(null,"compteA2",100.0));
		daoCompte.save(new Compte(null,"compteA3",160.0));
		daoCompte.save(new Compte(null,"compteA4",200.0));
		double soldeMini=120.0;
		List<Compte> comptes=daoCompte.findBySoldeGreaterThanEqual(soldeMini);
		logger.debug("comptes avec solde >= " + soldeMini + " = " + comptes );
		assertNotNull(comptes);
		assertTrue(comptes.size()>=2);
		for(Compte c : comptes) {
			assertTrue(c.getSolde()>=soldeMini);
		}
	}
	
	
	@Test
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void testFindByLabelLike(){
		daoCompte.save(new Compte(null,"compteA1",50.0));
		daoCompte.save(new Compte(null,"compteXyz",100.0));
		daoCompte.save(new Compte(null,"compteXxx",160.0));
		daoCompte.save(new Compte(null,"compteA4",200.0));
		
		//List<Compte> comptes=daoCompte.findByLabelLike("compteX%");
		List<Compte> comptes=daoCompte.findByLabelCommeCela("compteX%");
		logger.debug("comptes avec label like compteX + " + comptes );
		assertNotNull(comptes);
		//assertTrue(comptes.size()>=2);
		assertTrue(comptes.size()==2);

	}
	
	@Test
	public void testFindComptesWithOperations(){
		Compte cptAa = daoCompte.save(new Compte(null,"compteAa",50.0));
		Operation op1Aa = new Operation(null,"achat aa1" , -5.0 , new Date());
        op1Aa.setCompte(cptAa); daoOperation.save(op1Aa);
		Operation op2Aa = new Operation(null,"achat aa2" , -6.0 , new Date());
		op2Aa.setCompte(cptAa);daoOperation.save(op2Aa);
		
		Compte cptBb = daoCompte.save(new Compte(null,"compteBb",70.0));
        Operation op1Bb = new Operation(null,"achat bb1" , -8.0 , new Date());
        op1Bb.setCompte(cptBb); daoOperation.save(op1Bb);
		Operation op2Bb = new Operation(null,"achat bb2" , -9.0 , new Date());
		op2Bb.setCompte(cptBb);daoOperation.save(op2Bb);
		
		Compte cptAa_relu_avec_op = daoCompte.findWithOperations(cptAa.getNumero());
		assertNotNull(cptAa_relu_avec_op);
		assertTrue(cptAa_relu_avec_op.getOperations().size()>=2);
		for(Operation op : cptAa_relu_avec_op.getOperations()) {
			//assertTrue(op.getCompte())
			logger.debug("\t operation: " + op);
		}
		logger.debug("cptAa_relu_avec_op=" + cptAa_relu_avec_op);
	}

}
