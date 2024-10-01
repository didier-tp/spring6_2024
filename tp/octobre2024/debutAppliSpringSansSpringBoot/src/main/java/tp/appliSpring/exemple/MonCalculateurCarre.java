package tp.appliSpring.exemple;

import org.springframework.stereotype.Component;

@Component() //with default name/qualifier= "monCalculateurCarre"
public class MonCalculateurCarre implements MonCalculateur {

	@Override
	public double calculer(double x) {
		return x*x;
	}

}
