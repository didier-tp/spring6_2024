package tp.appliSpring.bank.core.init;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import tp.appliSpring.bank.core.model.Client;
import tp.appliSpring.bank.core.service.ServiceClient;
import tp.appliSpring.bank.core.service.ServiceCompte;
import tp.appliSpring.bank.core.service.ServiceOperation;
import tp.appliSpring.bank.core.model.Compte;
import tp.appliSpring.bank.core.model.Operation;

@Component
@Profile("reInit")
public class ReInitDefaultDataSet {
	
	@Autowired
	ServiceCompte serviceCompte;
	
	@Autowired
	ServiceOperation serviceOperation;

	@Autowired
	ServiceClient serviceClient;
	
	@PostConstruct
	public void intialiserJeuxDeDonnees() {
		System.out.println("initialisation d'un jeux de données (en mode developpement)");
		Compte cptA = serviceCompte.create(new Compte(null,"compteA",100.0));
		serviceOperation.create(new Operation(null,"achat 1" , -5.0 , new Date()),
				                              cptA.getNumero());
		serviceOperation.create(new Operation(null,"achat 2" , -6.0 , new Date()),
				                             cptA.getNumero());
		Compte cptB = serviceCompte.create(new Compte(null,"compteB",200.0));

		Client client1 = new Client(null, "alex", "Therieur", "12 rue Elle 75001 Paris", "email1");
		serviceClient.create(client1);

		Compte cptC = serviceCompte.create(new Compte(null,"compteC",20.0));
		Compte cptD = serviceCompte.create(new Compte(null,"compteD",30.0));

		Client client2 = new Client(null, "axelle", "Aire", "13 rue Elle 75001 Paris", "email2");
		serviceClient.create(client2);

	}

}
