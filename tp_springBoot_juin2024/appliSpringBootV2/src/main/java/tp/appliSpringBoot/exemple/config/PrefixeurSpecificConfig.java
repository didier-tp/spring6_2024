package tp.appliSpringBoot.exemple.config;

import org.mycontrib.basic.comp.BasicPrefixeur;
import org.mycontrib.basic.comp.Prefixeur;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//NB: si cette configuration est activée, elle est prioritaire
//par rapport à l'autoconfiguration comportant @ConditionOnMissingBean

//@Configuration
public class PrefixeurSpecificConfig  {
	
	@Bean
	public Prefixeur prefixeur() {
		String prefixe = "***>>>";
		return new BasicPrefixeur(prefixe);
	}
	
}
