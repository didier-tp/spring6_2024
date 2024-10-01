package tp.appliSpring.exemplev2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExempleAppV2 {
	public static void main(String[] args) {

		System.setProperty("spring.profiles.default", "V1");
		//System.setProperty("spring.profiles.default", "V2");
		
		ApplicationContext contextSpring = new AnnotationConfigApplicationContext(ExempleConfigExplicte.class);
		//contextSpring représente un ensemble de composants pris en charge par spring
		//et qui est initialisé selon une ou plusieurs classes de configuration.

		//MonCalculateur monCalculateur = contextSpring.getBean(MonCalculateur.class);
		MonCalculateur monCalculateur = contextSpring.getBean("monCalculateur", MonCalculateur.class);
		System.out.println("resCalcul="+monCalculateur.calculer(4));//4*4=16.0 ou autre 
		
		//A completer ...
		Coordinateur coordinateurPrisEnChargeParSpring =
				contextSpring.getBean(Coordinateur.class);
		coordinateurPrisEnChargeParSpring.calculerEtAfficher();

		CoordinateurAvecInjectionParConstructeur coordinateur2PrisEnChargeParSpring =
				contextSpring.getBean(CoordinateurAvecInjectionParConstructeur.class);
		coordinateur2PrisEnChargeParSpring.calculerEtAfficher();

		((AnnotationConfigApplicationContext) contextSpring).close();
	}
}
