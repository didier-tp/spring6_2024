package tp.appliSpring.exemplev2;

import org.springframework.stereotype.Component;

public class MonAfficheurV1 implements MonAfficheur {

	@Override
	public void afficher(String message) {
		System.out.println(">>"+message);

	}

	@Override
	public void afficherMaj(String message) {
		System.out.println(">>"+message.toUpperCase());
	}

}
