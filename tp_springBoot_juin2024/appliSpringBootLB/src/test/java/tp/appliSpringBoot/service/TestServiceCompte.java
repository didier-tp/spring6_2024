package tp.appliSpringBoot.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tp.appliSpringBoot.entity.Compte;
import tp.appliSpringBoot.repository.CompteRepository;
import tp.appliSpringBoot.repository.TestCompteRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@ActiveProfiles({ "h2"})
public class TestServiceCompte {

    Logger logger = LoggerFactory.getLogger(TestServiceCompte.class);

    @Autowired
    private ServiceCompte serviceCompte; //à tester

    @Autowired
    private CompteRepository compteRepository; //aider à tester

    @Test
    public void testVirement() {
        Compte cA = compteRepository.save(new Compte(null, "compteA", 50.0));
        Compte cB = compteRepository.save(new Compte(null, "compteB", 40.0));

        try {
            serviceCompte.transferer(10.0,cA.getNumero() , -cB.getNumero());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Compte cArelu = compteRepository.findById(cA.getNumero()).get();
        logger.debug("cArelu=" + cArelu);

        Compte cBrelu = compteRepository.findById(cB.getNumero()).get();
        logger.debug("cBrelu=" + cBrelu);
       //...
    }
}
