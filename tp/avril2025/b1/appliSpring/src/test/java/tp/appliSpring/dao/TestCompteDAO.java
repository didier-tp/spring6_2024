package tp.appliSpring.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import tp.appliSpring.entity.Compte;

@SpringBootTest
@ActiveProfiles("dev")//pour prendre en compte application-dev.properties
public class TestCompteDAO {
	
	@Autowired
	private CompteDAO compteDAO;//à tester
	
	@Test
	public void testCrudCompte() {
		Compte cptA = new Compte(null,"compteA",50.0);
		compteDAO.save(cptA);//sauvegarder le compte en base de données
		//...
	}

}
