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

import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.entity.Compte;
import tp.appliSpring.service.ServiceCompte;

@RestController // composant spring pour WS REST
@RequestMapping(value = "/rest/bank-api/v1/comptes", headers = "Accept=application/json")
public class CompteRestController {

	@Autowired
	private ServiceCompte serviceCompte;

	// URL de déclenchement:
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@GetMapping("/{numero}")
	public ResponseEntity<CompteDto> getCompteByNum(@PathVariable("numero") Long numero) {
		try {
			CompteDto c = serviceCompte.searchDtoById(numero);
			return new ResponseEntity<CompteDto>(c, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<CompteDto>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	 * @GetMapping("/{numero}" ) public Compte
	 * getCompteByNum(@PathVariable("numero") Long numero) { return
	 * serviceCompte.searchById(numero); }
	 */

	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@DeleteMapping("/{numero}")
	public ResponseEntity<?> deleteCompteByNum(@PathVariable("numero") Long numero) {
		try {
			serviceCompte.removeById(numero);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204: OK mais sans détails/ sans message
		} catch (Exception e) {
			return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);
		}
	}

	// URL de déclenchement:
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes?soldeMini=60
	@GetMapping()
	public List<Compte> getComptesByCriteria(@RequestParam(value = "soldeMini", required = false) Double soldeMini) {
		if (soldeMini != null)
			return serviceCompte.searchBySoldeMini(soldeMini);
		/* else */
		return serviceCompte.searchAll();
	}

	// URL de déclenchement (en mode POST)
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes
	// avec { "numero" : null , "label" : "nouveau_compte" , "solde" : 50.0 }
	@PostMapping()
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

	// URL de déclenchement (en mode PUT)
	// http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/2
	// avec { "numero" : 2 , "label" : "compteBb" , "solde" : 65.0 }
	@PutMapping("/{id}")
	public ResponseEntity<Compte> putCompte(@RequestBody Compte obj, @PathVariable("id") Long idToUpdate) {
		obj.setNumero(idToUpdate);
		serviceCompte.updateCompte(obj);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		// 204 : OK sans aucun message dans partie body
	}

}
