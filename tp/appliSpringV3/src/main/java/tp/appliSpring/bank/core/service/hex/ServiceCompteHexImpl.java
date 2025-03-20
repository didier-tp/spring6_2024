package tp.appliSpring.bank.core.service.hex;

import java.util.List;

import tp.appliSpring.generic.service.hex.GenericServiceHexImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.appliSpring.bank.core.exception.BankException;
import tp.appliSpring.bank.core.hex.spi.CompteLoader;
import tp.appliSpring.bank.core.hex.spi.CompteSaver;
import tp.appliSpring.bank.core.service.ServiceCompte;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.core.model.Compte;

/*
implementation selon l'architecture hexagonale avec SPI et persistence adapter
 */


@Service //@Component de type Service
//@Transactional
@Qualifier("hex")
public class ServiceCompteHexImpl extends GenericServiceHexImpl<Compte,CompteEntity,Long> implements ServiceCompte {

	private CompteSaver compteSaver;
	private CompteLoader compteLoader;


	@Autowired
	public ServiceCompteHexImpl(CompteLoader compteLoader, CompteSaver compteSaver){
		super(Compte.class,compteLoader,compteSaver);
		this.compteLoader=compteLoader;
		this.compteSaver=compteSaver;
	}

	@Transactional()
	//@Transactional(propagation = Propagation.REQUIRED)par défaut
	public void transfer(double montant, long numCptDeb, long numCptCred)throws BankException {
		try {
			Compte cptDeb = compteLoader.loadById(numCptDeb).get();
			cptDeb.setSolde(cptDeb.getSolde() - montant);
			compteSaver.updateExisting(cptDeb);
			
			Compte cptCred = compteLoader.loadById(numCptCred).get();
			cptCred.setSolde(cptCred.getSolde() + montant);
			compteSaver.updateExisting(cptCred);
		} catch (Exception e) {
			throw new BankException("echec virement",e);
		}
	}


	@Override
	public List<Compte> searchWithMinimumBalance(double soldeMini) {
		return compteLoader.findBySoldeMini(soldeMini);
	}

	@Override
	public List<Compte> searchCustomerAccounts(Long numClient) {
		return compteLoader.findByCustomerNumber(numClient);
	}

	@Override
	public void fixerProprietaireCompte(long numCompte, long numClient) {
        compteSaver.setAccountOwnerNumber(numCompte, numClient);
	}
}
