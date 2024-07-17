package tp.appliSpringRennes.service;

import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.exception.BankException;

public interface ServiceCompte {
    Compte searchById(long id);
    Compte saveOrUpdate(Compte c);

    void deleteByNum(long numero);

    void transferer(double montant,long numCptDeb, long numCptCred) throws BankException;
}
