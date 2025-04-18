package tp.appliSpring.service;

import java.util.List;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;

public class ServiceCompteImpl implements ServiceCompte{

	@Override
	public Compte searchById(long numeroCompte) {
		// TODO Auto-generated method stub
		return null;
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