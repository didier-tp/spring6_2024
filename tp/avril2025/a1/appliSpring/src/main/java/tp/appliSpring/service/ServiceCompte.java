package tp.appliSpring.service;

import java.util.List;

import tp.appliSpring.entity.Compte;

public interface ServiceCompte {
	Compte searchById(long numeroCompte);
	List<Compte> searchAll();
	List<Compte> searchBySoldeMini(double soldeMini);
	Compte insertCompte(Compte c);
	void updateCompte(Compte c);
	void removeById(long numCompte);
	
    void transferer(double montant,long numCptDeb, long numCptCred);
}
