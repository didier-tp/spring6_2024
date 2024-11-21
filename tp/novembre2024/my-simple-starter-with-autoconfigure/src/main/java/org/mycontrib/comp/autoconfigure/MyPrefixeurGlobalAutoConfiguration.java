package org.mycontrib.comp.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "org.mycontrib.comp.config" /*, "..." */})
public class MyPrefixeurGlobalAutoConfiguration {
	
	private static Logger logger = LoggerFactory.getLogger(MyPrefixeurGlobalAutoConfiguration.class);
	
	public MyPrefixeurGlobalAutoConfiguration() {
	     logger.info("org.mycontrib.comp.autoconfigure.MyPrefixeurGlobalAutoConfiguration loaded (my-simple-starter-with-autoconfigure)");
	}
}
