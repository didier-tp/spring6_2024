package tp.appliSpringBoot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tp.appliSpringBoot.entity.Compte;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
public class TestCompteRepository {

    @Autowired
    private CompteRepository compteRepository; //à tester

    @Test
    public void testSauvegardeEtRelecture(){
        Compte cA = new Compte(null, "compteA", 50.0);
        cA = compteRepository.save(cA);  //avec auto_incr automatique
        System.out.println("cA=" +cA);

        Compte cArelu = compteRepository.findById(cA.getNumero()).get();
        System.out.println("cArelu=" +cArelu);
        assertEquals("compteA",cArelu.getLabel() );
        assertEquals(50.0 , cArelu.getSolde(), 0.00000);

    }


}
