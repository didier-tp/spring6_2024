package tp.appliSpring.core.init;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import tp.appliSpring.converter.Converter;
import tp.appliSpring.core.dao.DaoOperation;
import tp.appliSpring.core.entity.Operation;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.CompteDto;

@Component
@Profile("reInit")
public class ReInitDefaultDataSet {
	
	@Autowired
	ServiceCompte serviceCompte;
	
	@Autowired
	DaoOperation daoOperation;
	
	@Autowired
	Converter converter;
	
	@PostConstruct
	public void intialiserJeuxDeDonnees() {
		System.out.println("initialisation d'un jeux de données (en mode developpement)");
		CompteDto cptA = new CompteDto(null,"compteA",100.0);
		CompteDto cptA_sauvegarde = serviceCompte.sauvegarderCompte(cptA);
		
        Operation op1 = new Operation(null,"achat 1" , -5.0 , new Date());
        op1.setCompte(converter.compteDtoToCompte(cptA_sauvegarde)); daoOperation.save(op1);
		Operation op2 = new Operation(null,"achat 2" , -6.0 , new Date());
		op2.setCompte(converter.compteDtoToCompte(cptA_sauvegarde));daoOperation.save(op2);
		
	    serviceCompte.sauvegarderCompte(new CompteDto(null,"compteB",200.0));
	    serviceCompte.sauvegarderCompte(new CompteDto(null,"compteC",20.0));
	    serviceCompte.sauvegarderCompte(new CompteDto(null,"compteD",30.0));
	
	}

}
