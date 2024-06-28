package tp.appliSpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpringBoot.entity.Compte;
import tp.appliSpringBoot.repository.CompteRepository;

@Service
@Transactional
public class ServiceCompteImpl implements ServiceCompte {

    @Autowired
    private CompteRepository compteRepository;

    @Override
    public void transferer(Double montant, long numCptDeb, long numCptCred) {
          Compte cptDeb = compteRepository.findById(numCptDeb).get();
          cptDeb.setSolde(cptDeb.getSolde() - montant);
          compteRepository.save(cptDeb);

          Compte cptCred = compteRepository.findById(numCptCred).get();
          cptCred.setSolde(cptCred.getSolde() + montant);
          compteRepository.save(cptCred);
    }
}
