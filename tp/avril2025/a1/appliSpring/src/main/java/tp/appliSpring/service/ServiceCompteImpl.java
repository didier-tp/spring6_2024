package tp.appliSpring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.appliSpring.dao.CompteDao;
import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.entity.Compte;
import tp.appliSpring.exception.BankException;

@Service //composant spring de type service métier
//@Transactional
public class ServiceCompteImpl implements ServiceCompte{
	
	//@Autowired //injection de dépendance (par correspondance de type)
	private CompteDao compteDao;
	
	
	//injection de dépendance par constructeur (avec @Autowired implicite , sous entendu)
	public ServiceCompteImpl(CompteDao compteDao) {
		this.compteDao = compteDao;
	}

	@Override
	public Compte searchById(long numeroCompte) {
		return compteDao.findById(numeroCompte).get();
	}

	@Override
	public List<Compte> searchAll() {
		return compteDao.findAll();
	}

	@Override
	public List<Compte> searchBySoldeMini(double soldeMini) {
		return compteDao.findSelonSoldeMini(soldeMini);
	}

	@Override
	public Compte insertCompte(Compte c) {
		return compteDao.save(c); //on retourne le compte inséré en base avec la clef primaire auto incrémenté
	}

	@Override
	public void updateCompte(Compte c) {
		compteDao.save(c);
	}

	@Override
	public void removeById(long numCompte) {
		compteDao.deleteById(numCompte);
		
	}

	@Override
	@Transactional
	public void transferer(double montant, long numCptDeb, long numCptCred) throws BankException {
		try {
			Compte cptDeb = compteDao.findById(numCptDeb).get();
			cptDeb.setSolde(cptDeb.getSolde()-montant);
			compteDao.save(cptDeb);
			
			Compte cptCred = compteDao.findById(numCptCred).get();
			cptCred.setSolde(cptCred.getSolde()+montant);
			compteDao.save(cptCred);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new BankException("echec virement", e);
		}
	}

	@Override
	public CompteDto searchDtoById(long numeroCompte) {
		Compte compteEntity = compteDao.findById(numeroCompte).get();
		return new CompteDto(compteEntity.getNumero(),
				              compteEntity.getLabel(),
				              compteEntity.getSolde());
	}
	
}