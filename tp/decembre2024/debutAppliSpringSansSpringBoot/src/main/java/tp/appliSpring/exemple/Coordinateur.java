package tp.appliSpring.exemple;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Coordinateur {
	
	@Autowired @Qualifier("v2")
	private MonAfficheur monAfficheur; //référence vers afficheur à injecter

	@Autowired @Qualifier("monCalculateurCarre")
	private MonCalculateur monCalculateur=null;//référence vers calculateur à injecter
	
	public Coordinateur(){
		System.out.println("dans le constructeur de Coordinateur() , monAfficheur="+monAfficheur);
	}

	@PostConstruct
	public void initialiser(){
		System.out.println("dans initialiser() prefixé par @PostConstruct, monAfficheur="+monAfficheur);
	}

	public void calculerEtAfficher() {
		double x=4;
		double res =monCalculateur.calculer(x); //x*x ou bien 2*x ou bien ...
		monAfficheur.afficher("res="+res);// >> res=16 en v1 ou bien ** res=16
	}
}
