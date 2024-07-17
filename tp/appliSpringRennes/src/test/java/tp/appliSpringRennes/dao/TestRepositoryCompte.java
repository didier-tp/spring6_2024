package tp.appliSpringRennes.dao;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.entity.Operation;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"dev"})
public class TestRepositoryCompte {

    Logger logger = LoggerFactory.getLogger(TestRepositoryCompte.class);

    @Autowired
    private RepositoryCompte repositoryCompte; //à tester

    @Autowired
    private RepositoryOperation repositoryOperation; //pour aider à tester

    @Test
    public void testFindByIdWithOperations(){
        //créer un compte et le sauvegarder
        Compte cXy = repositoryCompte.save(new Compte(null,"compteXy",150.0));

        //créer des opérations rattachées au compte et les sauvegarder
        Operation op1Cxy = new Operation(null,"achat bonbon",-3.7, new Date());
        op1Cxy.setCompte(cXy);
        repositoryOperation.save(op1Cxy);

        Operation op2Cxy = new Operation(null,"achat boisson",-2.6, new Date());
        op2Cxy.setCompte(cXy);
        repositoryOperation.save(op2Cxy);

        //relire et vérifier
        //Compte cXyRelu = repositoryCompte.findById(cXy.getNumero()).get(); //avec lazy exception
        Compte cXyRelu = repositoryCompte.findByIdWithOperations(cXy.getNumero());
        assertTrue(cXyRelu.getOperations().size()==2);
        logger.debug("operations rattachees a cXyRelu=" + cXyRelu.getOperations());
    }
    @Test
    public void testCrudCompte(){
       //créer un nouveau compte
        Compte cA = new Compte(null,"compteA",50.0);
        //le sauvegarder en base
        cA = repositoryCompte.save(cA);
        //le relire et vérifier valeurs correctes:
        Compte caRelu = repositoryCompte.findById(cA.getNumero()).get();
        logger.debug("caRelu="+caRelu);
        assertNotNull(caRelu.getNumero());
        assertEquals("compteA",caRelu.getLabel());
        assertEquals(50.0 , caRelu.getSolde(),0.00001);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testComptesBySoldeMini(){
       repositoryCompte.save(new Compte(null,"compteCa",50.0));
       repositoryCompte.save(new Compte(null,"compteCb",80.0));
       repositoryCompte.save(new Compte(null,"compteCc",40.0));

        List<Compte> comptes = repositoryCompte.findBySoldeGreaterThanEqual(45.0);
        assertTrue(comptes.size()>=2);
        logger.debug("comptesPlusGrandsQue45="+comptes);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testComptesBySoldeMaxi(){
        repositoryCompte.save(new Compte(null,"compteCaa",50.0));
        repositoryCompte.save(new Compte(null,"compteCbb",80.0));
        repositoryCompte.save(new Compte(null,"compteCcc",40.0));

        List<Compte> comptes = repositoryCompte.findBySoldePlusPetitQue(60.0);
        assertTrue(comptes.size()>=2);
        logger.debug("comptesPlusPetitsQue60="+comptes);
    }



}
