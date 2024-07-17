package tp.appliSpringRennes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpringRennes.annotation.LogExecutionTime;
import tp.appliSpringRennes.dao.RepositoryCompte;
import tp.appliSpringRennes.dto.CompteDto;
import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.exception.BankException;

import java.util.List;

@Service //@Component de type business service
//@Transactional possible (et conseillé) sur l'ensemble de la classe du service
public class ServiceCompteImpl implements ServiceCompte{

    private RepositoryCompte repositoryCompte;

    //@Autowired
    public ServiceCompteImpl(RepositoryCompte repositoryCompte) {
        this.repositoryCompte = repositoryCompte;
    }

    @Transactional(/*propagation = Propagation.REQUIRED*/)
    @LogExecutionTime
    @Override
    public void transferer(double montant, long numCptDeb, long numCptCred) {
        try {
            Compte cptDeb = repositoryCompte.findById(numCptDeb).get();
            if(cptDeb.getSolde()<montant)
                throw new BankException("solde insuffisant sur compte a débiter");
            cptDeb.setSolde(cptDeb.getSolde()-montant);
            repositoryCompte.save(cptDeb);

            Compte cptCred = repositoryCompte.findById(numCptCred).get();
            cptCred.setSolde(cptCred.getSolde()+montant);
            repositoryCompte.save(cptCred);
        } catch (Exception e) {
            throw new BankException("echec transfert" ,e);
        }
    }

    @Override
    public List<Compte> getAllComptes() {
        return repositoryCompte.findAll();
    }

    @Override
    public List<Compte> getComptesBySoldeMini(Double soldeMini) {
        return repositoryCompte.findBySoldeGreaterThanEqual(soldeMini);
    }

    @Override
    @LogExecutionTime
    public Compte searchById(long id) {
        return repositoryCompte.findById(id).get();
        //return repositoryCompte.findById(id).orElse(null);
    }

    @Override
    public Compte saveOrUpdate(Compte c) {
        return repositoryCompte.save(c);
    }

    @Override
    public void deleteByNum(long numero) {
        repositoryCompte.deleteById(numero);
    }


}
