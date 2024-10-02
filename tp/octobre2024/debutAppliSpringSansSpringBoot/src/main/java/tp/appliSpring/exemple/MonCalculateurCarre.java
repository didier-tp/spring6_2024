package tp.appliSpring.exemple;

import org.springframework.stereotype.Component;
import tp.appliSpring.annotation.LogExecutionTime;

@Component() //with default name/qualifier= "monCalculateurCarre"
public class MonCalculateurCarre implements MonCalculateur {

	@Override
	@LogExecutionTime
	public double calculer(double x) {
		return x*x;
	}

}
