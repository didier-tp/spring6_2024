package tp.appliSpringRennes.init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tp.appliSpringRennes.dao.RepositoryOperation;
import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.entity.Operation;
import tp.appliSpringRennes.service.ServiceCompte;

import java.util.Date;

@Component
@Profile("dev") // ou @Profile("reInit")
public class ReInitDefaultDataSet {
    @Autowired
    private RepositoryOperation repositoryOperation;

    @Autowired
    private ServiceCompte serviceCompte;

    @PostConstruct
    public void initDataSet() {
        Compte cA= serviceCompte.saveOrUpdate(new Compte(null, "CompteA", 50.0));
        serviceCompte.saveOrUpdate(new Compte(null, "CompteB", 80.0));

        Operation op1Ca = new Operation(null,"achat bonbon",-3.7, new Date());
        op1Ca.setCompte(cA);
        repositoryOperation.save(op1Ca);
    }
}
