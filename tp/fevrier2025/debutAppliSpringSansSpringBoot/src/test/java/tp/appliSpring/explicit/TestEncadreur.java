package tp.appliSpring.explicit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tp.appliSpring.explicit.beans.Encadreur;
import tp.appliSpring.explicit.conf.ExempleConfigExplicite;

@ExtendWith(SpringExtension.class) //si junit5/jupiter
@ContextConfiguration(classes= {ExempleConfigExplicite.class})
@ActiveProfiles(profiles= {"maj"})
public class TestEncadreur {
	
	@Autowired
	private Encadreur encadreur; //à tester
	
	@Test
	public void testEncadrer() {
		String res = encadreur.encadrer("java");
		assertEquals(">>>JAVA<<<",res); //si profile "maj" activé au démarrage du test
	}

}
