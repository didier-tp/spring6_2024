package tp.appliSpring.bank.web.api.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tp.appliSpring.bank.core.model.Compte;
import tp.appliSpring.bank.core.service.ServiceCompte;

@RestController // @Component de type controller d'api rest
@RequestMapping(value = "/rest/api-bank/v1/comptes", headers = "Accept=application/json")
public class CompteRestCtrl {

	/*
	 * //Code potentiellement en erreur à ne pas reproduire:
	 * 
	 * @Autowired private CompteRepository compteRepository;
	 * 
	 * @GetMapping("/{id}") public CompteEntity
	 * badVersionWithoutDtoForGetCompteById(@PathVariable("id") long numeroCompte) {
	 * return compteRepository.findById( numeroCompte).get(); //NB: plantage si
	 * pasde @JsonIgnore et généralement sans_DTO = très mauvaise pratique }
	 */

	private ServiceCompte serviceCompte;

	@Autowired
	public CompteRestCtrl(ServiceCompte serviceCompte) {
		this.serviceCompte = serviceCompte;
	}

	// Get By ID
	// V1 avec DTO et V3 (avec automatisme ExceptionHandler)
	// declencher en mode GET avec
	// http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1 ou 2
	@GetMapping("/{id}")
	public Compte getCompteById(@PathVariable("id") long numeroCompte) {
		return serviceCompte.searchById(numeroCompte);
		// NB: l'objet retourné sera automatiquement converti au format json
	}

	/*
	 * //V2 avec ResponseEntity<?> mais sans ExceptionHandler //declencher en mode
	 * GET avec //http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1 ou 2
	 * 
	 * @GetMapping("/{id}") ....
	 */

	// GET Multiple
	// http://localhost:8181/appliSpring/rest/api-bank/v1/comptes
	// http://localhost:8181/appliSpring/rest/api-bank/v1/comptes?soldeMini=50
	@GetMapping()
	public List<Compte> getComptesByCriteria(@RequestParam(value = "soldeMini", required = false) Double soldeMini) {
		if (soldeMini == null)
			return serviceCompte.searchAll();
		else
			return serviceCompte.searchByIdWithMinimumBalance(soldeMini);
	}

	// appelé en mode POST
	// avec url = http://localhost:8181/appliSpring/rest/api-bank/v1/comptes
	// avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	@PostMapping("")
	public ResponseEntity<?> postCompte(/* @Valid */ @RequestBody Compte compte) {
		Compte savedCompte = serviceCompte.create(compte); // avec id auto_incrémenté
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCompte.getNumero()).toUri();
		// return ResponseEntity.created(location).build();
		// return 201/CREATED , no body but URI to find added obj
		return ResponseEntity.created(location).body(savedCompte);
		// return 201/CREATED with savedObj AND with URI to find added obj
		/*
		 * ou bien encore return ResponseEntity.ok()
		 * .headers(responseHeadersWithLocation).body(savedObj);
		 */
	}

	// appelé en mode PUT
	// avec url = http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1
	// avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0 }
	@PutMapping("/{id}")
	public ResponseEntity<Compte> putCompte(@RequestBody Compte compte, @PathVariable("id") Long idToUpdate) {
		compte.setNumero(idToUpdate);
		Compte compteMisAJour = serviceCompte.update(compte);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 : OK sans aucun message dans partie body
		// execption handler may return NOT_FOUND or INTERNAL_SERVER_ERROR
	}

	// http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1 ou 2 (DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCompteByNum(@PathVariable("id")Long numCompte){
		serviceCompte.removeById(numCompte);
	        return new ResponseEntity<Compte>(HttpStatus.NO_CONTENT);
	//NO_CONTENT = 204 = OK mais sans message
	//exception handler may return NOT_FOUND or INTERNAL_SERVER_ERROR
	}
}
