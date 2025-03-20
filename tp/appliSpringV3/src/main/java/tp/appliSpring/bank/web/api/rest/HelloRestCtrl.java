package tp.appliSpring.bank.web.api.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tp.appliSpring.generic.dto.MessageDto;


@RestController //@Component de type controller d'api rest
@RequestMapping(value="/rest/api-bank/v1/hello" , headers="Accept=application/json")
/*
Basic @RestController for testing of @PreAuthorize("authenticated") , @PreAuthorize("hasRole('ADMIN')")
via MockMvc or ...
 */
public class HelloRestCtrl {

	private MessageDto sayHello(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return new MessageDto("Hello " + authentication.getPrincipal().toString());
	}

	//rest/api-bank/v1/hello/for_authenticated_user
	@PreAuthorize("authenticated")
	@GetMapping("/for_authenticated_user")
	public MessageDto getHelloMessageForAuthenticatedUser() {
		return sayHello();
	}

	//rest/api-bank/v1/hello/for_admin
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/for_admin")
	public MessageDto getHelloMessageForUserWithAdminRole() {
		return sayHello();
	}

}


