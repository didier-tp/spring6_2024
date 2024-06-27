package tp.appliSpringBoot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import tp.appliSpringBoot.entity.Compte;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@ActiveProfiles({ "h2"})
public class TestCompteRepository {

    @Autowired
    private CompteRepository compteRepository; //à tester

    @Test
    public void testSauvegardeEtRelecture() {
        Compte cA = new Compte(null, "compteA", 8050.0);
        cA = compteRepository.save(cA);  //avec auto_incr automatique
        System.out.println("cA=" + cA);

        Compte cArelu = compteRepository.findById(cA.getNumero()).get();
        System.out.println("cArelu=" + cArelu);
        assertEquals("compteA", cArelu.getLabel());
        assertEquals(8050.0, cArelu.getSolde(), 0.00000);

        compteRepository.deleteById(cA.getNumero());
    }

    @Test
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testFindBySoldeMini() {

        compteRepository.save(new Compte(null, "compteX", 7950.0));
        compteRepository.save(new Compte(null, "compteY", 8950.0));
        compteRepository.save(new Compte(null, "compteZ", 6950.0));
        compteRepository.save(new Compte(null, "compteZ2", 7950.0));

        List<Compte> comptes = compteRepository.findBySoldeGreaterThanEqual(7000.0);
        System.out.println("comptes=" + comptes);
        assertEquals(3, comptes.size());
    }

    @Test
    //@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testFindLabelLikeThis() {

        compteRepository.save(new Compte(null, "cc1", 2.0));
        compteRepository.save(new Compte(null, "cc2", 3.0));
        compteRepository.save(new Compte(null, "CompteX", 4.0));
        compteRepository.save(new Compte(null, "CompteY", 5.0));

        List<Compte> comptes = compteRepository.findByLabelLikeThis("cc%");
        System.out.println("comptes=" + comptes);
        assertEquals(2, comptes.size());
    }
}
