package tp.appliSpringRennes.service;

import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.exception.BankException;
import tp.appliSpringRennes.exception.MyNotFoundException;

import java.util.List;

public interface ServiceCompte {
    Compte searchById(long id) throws MyNotFoundException;
    Compte saveOrUpdate(Compte c);

    void verifExisting(long id)throws MyNotFoundException;

    void deleteByNum(long numero);

    void transferer(double montant,long numCptDeb, long numCptCred) throws BankException;

    List<Compte> getAllComptes();

    List<Compte> getComptesBySoldeMini(Double soldeMini);
}
