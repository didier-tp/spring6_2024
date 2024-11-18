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
import tp.appliSpring.exemple.ExempleConfig;
import tp.appliSpring.exemple.MonCalculateurCarre;


//@RunWith(SpringRunner.class)  //si junit4
@ExtendWith(SpringExtension.class) //si junit5/jupiter
@ContextConfiguration(classes= {ExempleConfigExplicite.class})
@ActiveProfiles({"v2"})
public class TestMonAfficheur {
	
	private static Logger logger = LoggerFactory.getLogger(TestMonAfficheur.class);
	
	@Autowired
	private MonAfficheur monAfficheur; //à tester
	
	@Test
	public void testAff() {
		monAfficheur.afficher("ok");
        //...

	}

}
