package tp.appliSpring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.appliSpring.dao.CompteDao;
import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;

@Service //composant spring de type service métier
public class ServiceCompteImpl implements ServiceCompte{
	
	@Autowired //injection de dépendance (par correspondance de type)
	private CompteDao compteDao;

	@Override
	public Compte searchById(long numeroCompte) {
		return compteDao.findById(numeroCompte).get();
	}

	@Override
	public List<Compte> searchAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compte> searchBySoldeMini(double soldeMini) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compte insertCompte(Compte c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCompte(Compte c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeById(long numCompte) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferer(double montant, long numCptDeb, long numCptCred) throws BankException {
		// TODO Auto-generated method stub
		
	}
	
}