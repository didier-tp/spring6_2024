package tp.appliSpringBoot.exemple;

import org.mycontrib.basic.comp.Prefixeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class PrefixeurExemple {
	
	@Autowired(required=false)
	private Prefixeur prefixeur;
	
	@PostConstruct
	public void exemple() {
		System.out.println("prefixeur="+prefixeur);
		if(prefixeur!=null) {
			String sWithPrefix = prefixeur.prefixer("SpringBoot");
			System.out.println("sWithPrefix="+sWithPrefix);
		}
	}

}
