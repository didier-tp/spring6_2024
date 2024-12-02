package tp.appliSpring.exemple;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component @Qualifier("v1")
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
