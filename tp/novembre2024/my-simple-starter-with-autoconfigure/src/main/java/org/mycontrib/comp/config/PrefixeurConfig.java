package org.mycontrib.comp.config;

import org.mycontrib.basic.comp.BasicPrefixeur;
import org.mycontrib.basic.comp.Prefixeur;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrefixeurConfig  {
	
	//prefixeur.prefixe de application.properties
	//ou bien "_" en tant que valeur par défaut
	@Value("${prefixeur.prefixe:_}") //${propertyName:defaultValue}
	private String prefixe; 
	
	@Bean
	@ConditionalOnMissingBean(Prefixeur.class)
	public Prefixeur prefixeur() {
		return new BasicPrefixeur(prefixe);
	}
	
}
