
package xy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xy.properties.SecurityProperties;

@Configuration
@ConfigurationPropertiesScan("xy.properties")
public class MySecurityConfig {

    @Autowired(required = false)
    public SecurityProperties securityProperties;

    @Bean(name="permitListAsString")
    @ConditionalOnMissingBean(name="permitListAsString")
    public String permitListAsString(){
        System.out.println("autoconfig");
        if(securityProperties!=null && securityProperties.getArea()!=null) {
            return securityProperties.getArea().getPermitList();
        }else {
            return ""; //par défaut
        }
    }

}

