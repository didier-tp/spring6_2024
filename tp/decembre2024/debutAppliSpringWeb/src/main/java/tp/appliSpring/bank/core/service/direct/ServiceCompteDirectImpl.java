package tp.appliSpring.bank.core.service.direct;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import tp.appliSpring.generic.exception.EntityNotFoundException;
import tp.appliSpring.generic.service.direct.GenericServiceDirectImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tp.appliSpring.generic.converter.GenericMapper;
import tp.appliSpring.bank.core.exception.BankException;
import tp.appliSpring.bank.core.service.ServiceCompte;
import tp.appliSpring.bank.persistence.repository.CompteRepository;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.core.model.Compte;

import java.util.List;

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
	private ObservationRegistry observationRegistry;

	@Autowired
	public ServiceCompteDirectImpl(CompteRepository daoCompte,ObservationRegistry observationRegistry){
		super(Compte.class,CompteEntity.class,daoCompte);
		this.daoCompte=daoCompte;
		this.observationRegistry=observationRegistry;
	}

	@Override
	@Observed(name = "searchById")
	//.../actuator/metrics/searchById
	public Compte searchById(Long aLong) throws EntityNotFoundException {
		return super.searchById(aLong);
	}
	/*
	{"name":"searchById","baseUnit":"seconds",
	"measurements":[
		{"statistic":"COUNT","value":6.0},
		{"statistic":"TOTAL_TIME","value":0.0253145},
		{"statistic":"MAX","value":0.0140763}],
	"availableTags":[
		{"tag":"method","values":["searchById"]},
		{"tag":"error","values":["none"]},
		{"tag":"class","values":["tp.appliSpring.bank.core.service.direct.ServiceCompteDirectImpl"]}
		]
	}
	 */

	@Override
	public Compte create(Compte obj) {
		//.../actuator/metrics/CREATED_ACCOUNT_STAT
		return Observation
				.createNotStarted("CREATED_ACCOUNT_STAT", observationRegistry)
				//.lowCardinalityKeyValue("xxx", "yyyy") //bounded (non infinite) set size
				.observe(() -> {
					// Execute business logic here
					Compte compte =  super.create(obj);
					/*
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    */
                    //System.out.println("a new account was added : " + obj);
					return compte;
				});
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
	//@Observed(name = "searchWithMinimumBalance")
	public List<Compte> searchByIdWithMinimumBalance(double soldeMini) {
		List<CompteEntity> compteEntityList = daoCompte.findBySoldeGreaterThanEqual(soldeMini);
		return GenericMapper.MAPPER.map(compteEntityList,Compte.class);
	}


}
