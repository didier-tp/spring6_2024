package tp.appliSpring.core.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.appliSpring.aspect.MesurerPerf;
import tp.appliSpring.converter.Converter;
import tp.appliSpring.core.dao.DaoCompte;
import tp.appliSpring.core.dao.DaoOperation;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.entity.Operation;
import tp.appliSpring.core.exception.BankException;
import tp.appliSpring.core.exception.NotFoundException;
import tp.appliSpring.dto.CompteDto;

@Service //@Component de type Service
//@Transactional
public class ServiceCompteImpl implements ServiceCompte {
	
	Logger logger = LoggerFactory.getLogger(ServiceCompteImpl.class);
	
	@Autowired
	private DaoCompte daoCompte;
	
	@Autowired
	private DaoOperation daoOperation;
	
	@Autowired
	private Converter converter;

	@Transactional()
	@MesurerPerf
	//@Transactional(propagation = Propagation.REQUIRED)par défaut
	public void transferer(double montant, long numCptDeb, long numCptCred)throws BankException {
		try {
			Compte cptDeb = daoCompte.findById(numCptDeb).get();
			cptDeb.setSolde(cptDeb.getSolde() - montant);
			daoCompte.save(cptDeb);
			//Operation(Long numero, String label, Double montant, Date dateOp)
			Operation opDebit=new Operation(null,"debit_suite_au_virement",-montant,new Date());
			opDebit.setCompte(cptDeb);
			daoOperation.save(opDebit);
			
			Compte cptCred = daoCompte.findById(numCptCred).get();
			cptCred.setSolde(cptCred.getSolde() + montant);
			daoCompte.save(cptCred);
			Operation opCredit= new Operation(null,"credit_suite_au_virement",montant,new Date());
			opCredit.setCompte(cptCred);
			daoOperation.save(opCredit);
			
			logger.debug(opDebit.toString());
			logger.debug(opCredit.toString());
		} catch (Exception e) {
			throw new BankException("echec virement",e);
		}
	}

	@Override
	@Transactional
	@MesurerPerf
	public CompteDto rechercherCompte(long numCpt)throws NotFoundException {
		try {
			//Compte cpt  =daoCompte.findById(numCpt);
			//for(Operation op : cpt.getOperations()); //bidouille affreuse pour eviter lazy
			//cpt.getOperations().size();
			
			Compte cpt = daoCompte.findWithOperations(numCpt);
			if(cpt==null) throw new NotFoundException("account not found with numCpt="+numCpt);
			return converter.compteToCompteDto(cpt);
		} catch (Exception e) {
			throw new NotFoundException("account not found with numCpt="+numCpt,e);
		}
	}

	@Override
	public CompteDto sauvegarderCompte(CompteDto cpt) {
		//en entré un dto avec id encore inconnu (à null)
		Compte c=  daoCompte.save(converter.compteDtoToCompte(cpt)); //avec pk auto_incr
		return converter.compteToCompteDto(c);
	}
	
	@Override
	public CompteDto updateCompte(CompteDto cpt)throws NotFoundException {
		if(!daoCompte.existsById(cpt.getNumero()))
			throw new NotFoundException("account not found with numero="+cpt.getNumero());
		Compte c=  daoCompte.save(converter.compteDtoToCompte(cpt)); 
		return converter.compteToCompteDto(c);
	}
	
	@Override
	public void deleteCompte(Long numCpt)throws NotFoundException {
		if(!daoCompte.existsById(numCpt))
			throw new NotFoundException("account not found with numero="+numCpt);
		daoCompte.deleteById(numCpt);
	}

	
	
	@Override
	public List<CompteDto> rechercherTousLesComptes() {
		return converter.listCompteToListCompteDto(daoCompte.findAll());
	}

	@Override
	public List<CompteDto> rechercherComptesAvecSoldeMini(double soldeMini) {
		return converter.listCompteToListCompteDto(daoCompte.findBySoldeGreaterThanEqual(soldeMini));
	}

}
