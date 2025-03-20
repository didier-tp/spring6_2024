package xy;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public class MyDefaultPermissionConfigurer implements MyPermissionConfigurer{

    private String[] permitAllArray;
    private String[] permitGetArray;
    private String[] authArray;

    public MyDefaultPermissionConfigurer(String[] permitAllArray, String[] permitGetArray, String[] authArray) {
        this.permitAllArray = permitAllArray;
        this.permitGetArray = permitGetArray;
        this.authArray = authArray;
    }

    public AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry
    addPermissionsFromAreaConfig(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizationManagerRequestMatcherRegistry) {

        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeRequestsWithPermissions = authorizationManagerRequestMatcherRegistry;

        if(permitAllArray.length>0)
            authorizeRequestsWithPermissions=authorizeRequestsWithPermissions
                    .requestMatchers(permitAllArray).permitAll();
/*
        if(denyArray.length>0)
            authorizeRequestsWithPermissions=authorizeRequestsWithPermissions
                    .requestMatchers(denyArray).denyAll();
*/
        if(permitGetArray.length>0)
            authorizeRequestsWithPermissions=authorizeRequestsWithPermissions
                    .requestMatchers(HttpMethod.GET, permitGetArray).permitAll();

        if(authArray.length>0)
            authorizeRequestsWithPermissions=authorizeRequestsWithPermissions
                    .requestMatchers(authArray).authenticated();

        return authorizeRequestsWithPermissions;
    }
}
