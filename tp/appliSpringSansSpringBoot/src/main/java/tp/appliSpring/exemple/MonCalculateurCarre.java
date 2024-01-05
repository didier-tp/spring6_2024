package tp.appliSpring.exemple;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary  //version principale (à utiliser si plusieurs versions possibles et si pas de @Qualifier)
@Component()//id par defaut = monCalculateurCarre = NomClasse avec minuscule au debut
public class MonCalculateurCarre implements MonCalculateur {

	@Override
	public double calculer(double x) {
		return x*x;
	}

}
