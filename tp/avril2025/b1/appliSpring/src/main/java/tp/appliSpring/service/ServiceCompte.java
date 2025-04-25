package tp.appliSpring.service;

import java.util.List;

import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;


/*
 * bonne pratique sur projet : partout des DTOs en entree et sortie des méthodes publiques
 * EXCEPTIONNELLEMENT EN TP : sur searchDtoByNumero() seulement
 */

public interface ServiceCompte {
	Compte searchByNumero(long num);
	CompteDto searchDtoByNumero(long num);
	List<Compte> searchAll();
	List<Compte> searchSelonSoldeMini(double soldeMini);
	Compte insertCompte(Compte c);
	void updateCompte(Compte c);
	void deleteCompteByNum(long num);
	
	void transferer(double montant,long numCptDeb,long numCptCred) throws BankException;
}
