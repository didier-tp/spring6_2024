package tp.appliSpring.service;

import java.util.List;

import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;

/*
 * BONNE PRATIQUE : Tout faire avec des CompteDto en entrée/sorties de la partie publique
 * EXCEPTIONNELEMENT EN TP : que sur la partie searchDtoById
 */


public interface ServiceCompte {
	Compte searchById(long numeroCompte);
	CompteDto searchDtoById(long numeroCompte);
	List<Compte> searchAll();
	List<Compte> searchBySoldeMini(double soldeMini);
	Compte insertCompte(Compte c);
	void updateCompte(Compte c);
	void removeById(long numCompte);
	
    void transferer(double montant,long numCptDeb, long numCptCred) throws BankException;
}
