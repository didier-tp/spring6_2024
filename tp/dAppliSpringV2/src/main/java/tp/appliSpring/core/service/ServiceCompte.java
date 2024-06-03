package tp.appliSpring.core.service;

import java.util.List;

import tp.appliSpring.core.exception.BankException;
import tp.appliSpring.core.exception.NotFoundException;
import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.dto.CompteDtoEx;

//par defaut , les méthodes peuvent renvoyer RuntimeException
public interface ServiceCompte {
	public void transferer(double montant,long numCptDeb,long numCptCred)throws BankException;
	public CompteDto rechercherCompte(long numCpt)throws NotFoundException;
	public CompteDtoEx rechercherCompteAvecOperations(long numCpt)throws NotFoundException;
	public List<CompteDto> rechercherTousLesComptes(); //retourne liste vide si rien trouver
	public List<CompteDto> rechercherComptesAvecSoldeMini(double soldeMini); //retourne liste vide si rien trouver
	public CompteDto sauvegarderCompte(CompteDto cpt);
	public CompteDto updateCompte(CompteDto cpt)throws NotFoundException;
	public void deleteCompte(Long numCpt)throws NotFoundException;
	//...
}
