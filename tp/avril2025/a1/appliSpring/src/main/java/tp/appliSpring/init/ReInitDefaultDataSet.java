package tp.appliSpring.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.service.ServiceCompte;

@Component
@Profile("reinit")
public class ReInitDefaultDataSet {
	
	private ServiceCompte serviceCompte;

	@Autowired
	public ReInitDefaultDataSet(ServiceCompte serviceCompte) {
		this.serviceCompte = serviceCompte;
		serviceCompte.insertCompte(new Compte(null,"compteA",50.0));
		serviceCompte.insertCompte(new Compte(null,"compteB",60.0));
		serviceCompte.insertCompte(new Compte(null,"compteC",70.0));
	}
	
	
}
