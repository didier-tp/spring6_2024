package tp.appliSpring.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.entity.Compte;


@SpringBootTest()
//par defaut, @SpringBootTest va utiliser la config automatique de springBoot
//et le fichier application.properties

//pour tenir compte en plus de application-mysql.properties il faut ajouter @ActiveProfile
@ActiveProfiles("mysql")
public class TestCompteDao {
	
	Logger logger = LoggerFactory.getLogger(TestCompteDao.class);
	
	@Autowired
	private CompteDao compteDao;//composant à tester
	
	@Test
	public void testSelonSoldeMini() {
		compteDao.save(new Compte(null,"c1",500.0));
		compteDao.save(new Compte(null,"c2",600.0));
		compteDao.save(new Compte(null,"c3",700.0));
		//List<Compte> comptesAvecAuMoins550euros = compteDao.findSelonSoldeMini(550.0);
		List<Compte> comptesAvecAuMoins550euros = compteDao.findBySoldeGreaterThanEqual(550.0);
		assertTrue(comptesAvecAuMoins550euros.size()>=2);
		logger.debug("comptesAvecAuMoins550euros="+comptesAvecAuMoins550euros);
	}
	
	
	@Test
	public void testDao() {
		Compte cA = new Compte(null,"compteA",50.0);//nouveau compte pas encore sauvegardé
		cA /* compte sauvegardé */= compteDao.save(cA);
		logger.debug(cA.toString());
		
		Compte cARelu = compteDao.findById(cA.getNumero()).get();
		assertEquals("compteA",cARelu.getLabel());
		assertEquals(50.0,cARelu.getSolde(),0.000001);
		logger.debug(cARelu.toString());
	}

}
