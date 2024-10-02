package tp.appliSpring.exemple;

import org.springframework.stereotype.Component;
import tp.appliSpring.annotation.LogExecutionTime;

@Component //with default name/qualifier= "monCalculateurDouble"
public class MonCalculateurDouble implements MonCalculateur {

	@Override
	@LogExecutionTime
	public double calculer(double x) {
		return 2*x;
	}

}
