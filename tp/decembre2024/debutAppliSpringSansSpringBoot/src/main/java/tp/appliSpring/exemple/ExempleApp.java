package tp.appliSpring.exemple;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExempleApp {
	public static void main(String[] args) {

		System.setProperty("spring.profiles.default", "perf"); //pour activer les log de perf
		
		ApplicationContext contextSpring = new AnnotationConfigApplicationContext(ExempleConfig.class);
		//contextSpring représente un ensemble de composants pris en charge par spring
		//et qui est initialisé selon une ou plusieurs classes de configuration.
		
		//MonCalculateur monCalculateur = contextSpring.getBean(MonCalculateur.class); //ok si une seule version de MonCalculateur
		MonCalculateur monCalculateur = (MonCalculateur) contextSpring.getBean("monCalculateurCarre");
		//MonCalculateurCarre monCalculateur = (MonCalculateurCarre) contextSpring.getBean("monCalculateurCarre"); //erreur de type class jdk.proxy2.$Proxy27 si Create new scratch file from selection
		System.out.println("monCalculateur="+monCalculateur.toString());
		if(monCalculateur instanceof MonCalculateurCarre)
			System.out.println("monCalculateur est de type MonCalculateurCarre");
		else
			System.out.println("monCalculateur n'est pas de type MonCalculateurCarre");
		//MonCalculateur monCalculateur = contextSpring.getBean("monCalculateurCarre" , MonCalculateur.class);
		System.out.println("resCalcul="+monCalculateur.calculer(4));//4*4=16.0 ou autre 

		Coordinateur coordinateurPrisEnChargeParSpring =contextSpring.getBean(Coordinateur.class);
		System.out.println("coordinateurPrisEnChargeParSpring="+coordinateurPrisEnChargeParSpring.toString());
		coordinateurPrisEnChargeParSpring.calculerEtAfficher();

		CoordinateurAvecInjectionParConstructeur coordinateurPrisEnChargeParSpring2 =
				  contextSpring.getBean(CoordinateurAvecInjectionParConstructeur.class);
		coordinateurPrisEnChargeParSpring2.calculerEtAfficher();
		
		((AnnotationConfigApplicationContext) contextSpring).close();
	}
}
