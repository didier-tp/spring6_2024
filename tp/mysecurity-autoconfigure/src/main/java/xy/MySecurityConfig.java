
package xy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xy.properties.SecurityProperties;

@Configuration
@Import(MySecurityAreaConfig.class)
public class MySecurityConfig {

    @Autowired @Qualifier("permitAllListAsString")
    private String permitAllListAsString;

    @Autowired @Qualifier("permitGetListAsString")
    private String permitGetListAsString;

    @Autowired @Qualifier("authListAsString")
    private String authListAsString;

    static String[] asStringArray(String pathListWithSeparator){
        if(pathListWithSeparator==null || pathListWithSeparator.isEmpty())
            return new String[0];
        return pathListWithSeparator.split(";");
    }

    @Bean(name="myPermissionConfigurer")
    @ConditionalOnMissingBean(name="myPermissionConfigurer")
    public MyPermissionConfigurer myDefaultPermissionConfigurer(){
        return new MyDefaultPermissionConfigurer(
                asStringArray(permitAllListAsString),
                asStringArray(permitGetListAsString),
                asStringArray(authListAsString)
        );
    }

}

