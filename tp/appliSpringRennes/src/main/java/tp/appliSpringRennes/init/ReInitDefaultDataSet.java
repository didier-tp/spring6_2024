package tp.appliSpringRennes.init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.service.ServiceCompte;

@Component
@Profile("dev") // ou @Profile("reInit")
public class ReInitDefaultDataSet {

    @Autowired
    private ServiceCompte serviceCompte;

    @PostConstruct
    public void initDataSet() {
        serviceCompte.saveOrUpdate(new Compte(null, "CompteA", 50.0));
        serviceCompte.saveOrUpdate(new Compte(null, "CompteB", 80.0));
    }
}
