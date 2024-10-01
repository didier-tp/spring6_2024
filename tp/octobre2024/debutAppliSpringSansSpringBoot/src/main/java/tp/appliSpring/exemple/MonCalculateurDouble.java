package tp.appliSpring.exemple;

import org.springframework.stereotype.Component;

@Component //with default name/qualifier= "monCalculateurDouble"
public class MonCalculateurDouble implements MonCalculateur {

	@Override
	public double calculer(double x) {
		return 2*x;
	}

}
