package tp.appliSpring.bank.core.service.direct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.appliSpring.bank.core.exception.BankException;
import tp.appliSpring.bank.core.model.Compte;
import tp.appliSpring.bank.core.service.ServiceCompte;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.persistence.repository.CompteRepository;
import tp.appliSpring.generic.converter.GenericMapper;
import tp.appliSpring.generic.exception.ConflictException;
import tp.appliSpring.generic.service.direct.GenericServiceDirectImpl;

/*
implementation en s'appuyant directement sur le dao/repository
(sans abstraction spi.Loader/Saver et sans adaptateur de persistance)
 */


@Service //@Component de type Service
//@Transactional
@Qualifier("direct")
@Primary
public class ServiceCompteDirectImpl extends GenericServiceDirectImpl<Compte,CompteEntity,Long> implements ServiceCompte {

	private CompteRepository daoCompte;


	@Autowired
	public ServiceCompteDirectImpl(CompteRepository daoCompte){
		super(Compte.class,CompteEntity.class,daoCompte);
		this.daoCompte=daoCompte;
	}

	@Transactional()
	//@Transactional(propagation = Propagation.REQUIRED)par défaut
	public void transfer(double montant, long numCptDeb, long numCptCred)throws BankException {
		try {
			CompteEntity cptDeb = daoCompte.findById(numCptDeb).get();
			cptDeb.setSolde(cptDeb.getSolde() - montant);
			daoCompte.save(cptDeb);

			CompteEntity cptCred = daoCompte.findById(numCptCred).get();
			cptCred.setSolde(cptCred.getSolde() + montant);
			daoCompte.save(cptCred);
		} catch (Exception e) {
			throw new BankException("echec virement",e);
		}
	}


	@Override
	public List<Compte> searchByIdWithMinimumBalance(double soldeMini) {
		List<CompteEntity> compteEntityList = daoCompte.findBySoldeGreaterThanEqual(soldeMini);
		return GenericMapper.MAPPER.map(compteEntityList,Compte.class);
	}

	@Override
	public Compte create(Compte obj) {
		if(obj.getNumero()!= null && daoCompte.existsById(obj.getNumero()))
			throw new ConflictException("compte deja existant avec id=" + obj.getNumero());
		return super.create(obj);
	}
	
	

}
