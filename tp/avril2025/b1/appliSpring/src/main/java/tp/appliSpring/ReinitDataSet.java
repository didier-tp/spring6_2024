package tp.appliSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.service.ServiceCompte;

@Component //composant spring
@Profile("dev") //en mode "dev" seulement
public class ReinitDataSet {
	
	private ServiceCompte serviceCompte;

	@Autowired
	public ReinitDataSet(ServiceCompte serviceCompte) {
		this.serviceCompte = serviceCompte;
		//initialiser jeux de données par défaut en base:
		serviceCompte.insertCompte(new Compte(null,"compteA" ,50.0));
		serviceCompte.insertCompte(new Compte(null,"compteB" ,60.0));
		serviceCompte.insertCompte(new Compte(null,"compteC" ,70.0));
	}
	
	

}
