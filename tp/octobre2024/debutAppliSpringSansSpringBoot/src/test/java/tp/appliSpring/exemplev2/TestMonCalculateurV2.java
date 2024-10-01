package tp.appliSpring.exemplev2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tp.appliSpring.exemplev2.MonCalculateurCarre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


//@RunWith(SpringRunner.class)  //si junit4
@ExtendWith(SpringExtension.class) //si junit5/jupiter
@ContextConfiguration(classes= {ExempleConfigExplicte.class})
//@ActiveProfiles(profiles = { "V1"})
@ActiveProfiles(profiles = { "V2"})
public class TestMonCalculateurV2 {
	
	private static Logger logger = LoggerFactory.getLogger(TestMonCalculateurV2.class);
	
	@Autowired
	private MonCalculateurCarre monCalculateur; //à tester
	//private MonCalculateur monCalculateur; //à tester

	@Autowired
	private MonAfficheur monAfficheur;

    @Test
	public void testAfficheur(){
		Assertions.assertNotNull(monAfficheur);
		monAfficheur.afficher("ok");
	}
	
	@Test
	public void testCalculer() {
		double res = monCalculateur.calculer(4);
		logger.debug("pour x=4 , res="+res);
		assertEquals(16.0, res,0.00000001);
	}

}
