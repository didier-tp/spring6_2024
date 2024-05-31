package tp.appliSpring.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tp.appliSpring.core.exception.BankException;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.VirementRequest;
import tp.appliSpring.dto.VirementResponse;

@RestController //@Component de type controller d'api rest
@RequestMapping(value="/rest/api-bank/virement" , headers="Accept=application/json")
public class VirementRestCtrl {
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	//appelé en mode POST ou PUT
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/virement
	//avec dans la partie "body" de la requête
	// { "montant" : 50 , "numCptDeb" : 1 , "numCptCred" : 2  }

	@PostMapping( "")
	VirementResponse postVirement(@RequestBody VirementRequest virementReq) {
		VirementResponse virementResponse = 
				new VirementResponse(virementReq.getMontant(),virementReq.getNumCptDeb(), virementReq.getNumCptCred());
		try {
			serviceCompte.transferer(virementReq.getMontant(),
					                 virementReq.getNumCptDeb(), 
					                 virementReq.getNumCptCred());
			//si pas execption, tout va bien
			virementResponse.setStatus(true);
			virementResponse.setMessage("virement bien effectué");
		} catch (BankException e) {
			e.printStackTrace();
			virementResponse.setStatus(false);
			virementResponse.setMessage(e.getMessage());
		}
		return virementResponse;
	}

}
