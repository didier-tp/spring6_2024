package tp.appliSpring.exemplev2;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


public class Coordinateur {
	

	private MonAfficheur monAfficheur=null; //référence vers afficheur à injecter


	private MonCalculateur monCalculateur=null;//référence vers calculateur à injecter
	
	public Coordinateur(){
		System.out.println("Dans constructeur Coordinateur() ,monCalculateur="+monCalculateur);
		System.out.println("Dans constructeur Coordinateur() ,monAfficheur="+monAfficheur);
	}


	public void calculerEtAfficher() {
		double x=4;
		double res =monCalculateur.calculer(x); //x*x ou bien 2*x ou bien ...
		monAfficheur.afficher("res="+res);// >> res=16 en v1 ou bien ** res=16
	}

	public void setMonAfficheur(MonAfficheur monAfficheur) {
		this.monAfficheur = monAfficheur;
	}

	public void setMonCalculateur(MonCalculateur monCalculateur) {
		this.monCalculateur = monCalculateur;
	}
}
