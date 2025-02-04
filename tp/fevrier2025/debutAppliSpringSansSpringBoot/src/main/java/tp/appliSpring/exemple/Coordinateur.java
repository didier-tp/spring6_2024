package tp.appliSpring.exemple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;


@Component
public class Coordinateur {
	
	@Autowired /*@Qualifier("V1")*/ @Qualifier("V2")
	private MonAfficheur monAfficheur/*=null*/; //référence vers afficheur à injecter
	
	
	@Autowired @Qualifier("monCalculateurCarre")
	private MonCalculateur monCalculateur=null;//référence vers calculateur à injecter
	
	public Coordinateur(){
		System.out.println("dans le constructeur de Coordinateur() , this.monAfficheur= "+this.monAfficheur);
		//this.monAfficheur.afficher(...) déclencherait ici nullpointerException
	}
	
	@PostConstruct
	public void initialiser() {
		System.out.println("dans initialiser() préfixée par @PostConstruct , this.monAfficheur= "+this.monAfficheur);
		//this.monAfficheur.afficher(...) ne déclencherait ici pas d'erreur
	}

	public void calculerEtAfficher() {
		double x=4;
		double res =monCalculateur.calculer(x); //x*x ou bien 2*x ou bien ...
		monAfficheur.afficher("res="+res);// >> res=16 en v1 ou bien ** res=16
	}
}
