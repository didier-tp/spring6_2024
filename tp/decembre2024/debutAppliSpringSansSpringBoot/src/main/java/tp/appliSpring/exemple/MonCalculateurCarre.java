package tp.appliSpring.exemple;

import org.springframework.stereotype.Component;
import tp.appliSpring.annotation.LogExecutionTime;

@Component() //id/nom par defaut : monCalculateurCarre
public class MonCalculateurCarre implements MonCalculateur {

	@Override
	@LogExecutionTime
	public double calculer(double x) {
		return x*x;
	}

}
