package tp.appliSpring.service;

import java.util.List;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;

public interface ServiceCompte {
	Compte searchByNumero(long num);
	List<Compte> searchAll();
	List<Compte> searchSelonSoldeMini(double soldeMini);
	Compte insertCompte(Compte c);
	void updateCompte(Compte c);
	void deleteCompteByNum(long num);
	
	void transferer(double montant,long numCptDeb,long numCptCred) throws BankException;
}
