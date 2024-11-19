package tp.appliSpring.exemple;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tp.appliSpring.annotation.LogExecutionTime;

@Component
public class Coordinateur {
	

	@Autowired
	@Qualifier("monAfficheurV2")
	private MonAfficheur monAfficheur=null; //référence vers afficheur à injecter


	@Autowired @Qualifier("monCalculateurDouble")
	private MonCalculateur monCalculateur=null;//référence vers calculateur à injecter

	public Coordinateur(){
		//constructeur
		System.out.println("dans constructeur Coordinateur() , monAfficheur="+monAfficheur);
	}

	@PostConstruct
	public void init() {
		System.out.println("dans init() prefixé par @PostConstruct , monAfficheur="+monAfficheur);
	}
	

	@LogExecutionTime
	public void calculerEtAfficher() {
		double x=4;
		double res =monCalculateur.calculer(x); //x*x ou bien 2*x ou bien ...
		monAfficheur.afficher("res="+res);// >> res=16 en v1 ou bien ** res=16
	}
}
/*
NB: si pas de @Qualifier() et deux implémentations possibles de MonAfficheur:
No qualifying bean of type 'tp.appliSpring.exemple.MonAfficheur' available: expected single matching bean but found 2: monAfficheurV1,monAfficheurV2
 */
