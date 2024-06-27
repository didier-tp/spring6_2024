package tp.appliSpringBoot.init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.appliSpringBoot.entity.Compte;
import tp.appliSpringBoot.repository.CompteRepository;

@Component
@Profile("reinit")
public class ReinitDataSet {

    @Autowired
    private CompteRepository compteRepository; //à tester

    @PostConstruct
    public void initDataSet(){

            compteRepository.save(new Compte(null, "compteA", 50.0));  //avec auto_incr automatique
            compteRepository.save(new Compte(null, "compteB", 150.0));
            compteRepository.save(new Compte(null, "compteC", 250.0));
            compteRepository.save(new Compte(null, "compteD", 350.0));
    }
}
