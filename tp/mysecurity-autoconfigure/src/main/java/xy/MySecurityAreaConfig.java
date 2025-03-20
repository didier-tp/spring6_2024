
package xy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xy.properties.SecurityProperties;

@Configuration
@ConfigurationPropertiesScan("xy.properties")  //package where to found ...Properties class
public class MySecurityAreaConfig {

    @Autowired(required = false)
    public SecurityProperties securityProperties;

    @Bean(name="permitAllListAsString")
    @ConditionalOnMissingBean(name="permitAllListAsString")
    public String permitAllListAsString(){
        System.out.println("mysecurity-autoconfigure , permitAllListAsString");
        if(securityProperties!=null && securityProperties.getArea()!=null) {
            return securityProperties.getArea().getPermitAllList();
        }else {
            return ""; //par défaut
        }
    }

    @Bean(name="permitGetListAsString")
    @ConditionalOnMissingBean(name="permitGetListAsString")
    public String permitGetListAsString(){
        System.out.println("mysecurity-autoconfigure , permitGetListAsString");
        if(securityProperties!=null && securityProperties.getArea()!=null) {
            return securityProperties.getArea().getPermitGetList();
        }else {
            return ""; //par défaut
        }
    }

    @Bean(name="authListAsString")
    @ConditionalOnMissingBean(name="authListAsString")
    public String authListAsString(){
        System.out.println("mysecurity-autoconfigure , authListAsString");
        if(securityProperties!=null && securityProperties.getArea()!=null) {
            return securityProperties.getArea().getAuthList();
        }else {
            return ""; //par défaut
        }
    }





}

