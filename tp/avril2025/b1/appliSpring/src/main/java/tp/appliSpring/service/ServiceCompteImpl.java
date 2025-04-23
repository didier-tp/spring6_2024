package tp.appliSpring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.appliSpring.dao.CompteDAO;
import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;

@Service  //composant spring de type "service métier" 
//@Transactional
public class ServiceCompteImpl implements ServiceCompte{
	
	@Autowired //injection de dépendance selon correspondance de type
	//compteDAO va référencer un composant spring existant compatible avec
	//l'interface CompteDAO
	private CompteDAO compteDAO; 

	@Override
	public Compte searchByNumero(long num) {
		return compteDAO.findById(num).get();
	}
	
	@Override
	public Compte insertCompte(Compte c) {
		return compteDAO.save(c);
	}

	@Override
	public List<Compte> searchAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compte> searchSelonSoldeMini(double soldeMini) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void updateCompte(Compte c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCompteByNum(long num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferer(double montant, long numCptDeb, long numCptCred) throws BankException {
		// TODO Auto-generated method stub
		
	}
	
}