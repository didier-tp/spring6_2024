package tp.appliSpringRennes.service;

import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.exception.BankException;

import java.util.List;

public interface ServiceCompte {
    Compte searchById(long id);
    Compte saveOrUpdate(Compte c);

    void deleteByNum(long numero);

    void transferer(double montant,long numCptDeb, long numCptCred) throws BankException;

    List<Compte> getAllComptes();

    List<Compte> getComptesBySoldeMini(Double soldeMini);
}
