package tp.appliSpring.rest;

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

import tp.appliSpring.entity.Compte;
import tp.appliSpring.service.ServiceCompte;

@RestController // composant spring pour api REST
@RequestMapping(value = "/rest/bank-api/v1/comptes", headers = "Accept=application/json")
public class CompteRestController {

	@Autowired
	private ServiceCompte serviceCompte;

	// URL de déclenchement:
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@GetMapping("/{numero}")
	public ResponseEntity<Compte> getCompteByNum(@PathVariable("numero") long numero) {
		try {
			return new ResponseEntity<Compte>(serviceCompte.searchByNumero(numero), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);// 404
		}
	}
	/*
	 * @GetMapping("/{numero}") public Compte
	 * getCompteByNum(@PathVariable("numero")long numero) { return
	 * serviceCompte.searchByNumero(numero); }
	 */

	// URL de déclenchement:
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes?soldeMini=60
	@GetMapping()
	public List<Compte> getComptesByCriteria(@RequestParam(value = "soldeMini", required = false) Double soldeMini) {
		if (soldeMini != null)
			return serviceCompte.searchSelonSoldeMini(soldeMini);
		/* else */
		return serviceCompte.searchAll();
	}

	// URL de déclenchement:
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@DeleteMapping("/{numero}")
	public ResponseEntity<Compte> deleteCompteByNum(@PathVariable("numero") long numero) {
		serviceCompte.deleteCompteByNum(numero);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204 , OK mais sans details/content
	}

	// appelé en mode POST
	// avec url = http://localhost:8181/appliSpring/rest/bank-api/v1/comptes
	// avec dans la partie "body" de la requête { "numero" : null , "label" : "…." ,
	// "solde" : 50.0 }
	@PostMapping("")
	public ResponseEntity<?> postCompte(/* @Valid */ @RequestBody Compte obj) {
		Compte savedObj = serviceCompte.insertCompte(obj); // avec id auto_incrémenté
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedObj.getNumero()).toUri();
		// return ResponseEntity.created(location).build();
		// return 201/CREATED , no body but URI to find added obj
		return ResponseEntity.created(location).body(savedObj);
		// return 201/CREATED with savedObj AND with URI to find added obj
		/*
		 * ou bien encore return ResponseEntity.ok()
		 * .headers(responseHeadersWithLocation).body(savedObj);
		 */
	}

	// à appeler en mode PUT
	// avec url = http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	// avec dans la partie "body" de la requête { "id" : 1 , "label" : "..." ,
	// "solde" : 120.0 }
	@PutMapping("/{id}")
	public ResponseEntity<Compte> putCompte(@RequestBody Compte obj, @PathVariable("id") Long idToUpdate) {
		obj.setNumero(idToUpdate);
		serviceCompte.updateCompte(obj);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		// 204 : OK sans aucun message dans partie body

	}
}
