package tp.appliSpring.bank.web.api.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tp.appliSpring.bank.core.model.Compte;
import tp.appliSpring.bank.core.service.ServiceCompte;
import tp.appliSpring.bank.persistence.entity.CompteEntity;
import tp.appliSpring.bank.persistence.repository.CompteRepository;
import tp.appliSpring.bank.web.api.dto.CompteToCreate;
import tp.appliSpring.generic.dto.MessageDto;
import tp.appliSpring.generic.exception.EntityNotFoundException;

import java.net.URI;
import java.util.List;

@RestController //@Component de type controller d'api rest
@RequestMapping(value="/rest/api-bank/v1/comptes" , headers="Accept=application/json")
public class CompteRestCtrl {

    /*
	//Code potentiellement en erreur à ne pas reproduire:
	@Autowired
	private CompteRepository compteRepository;

	@GetMapping("/{id}")
	public CompteEntity badVersionWithoutDtoForGetCompteById(@PathVariable("id") long numeroCompte) {
		return compteRepository.findById( numeroCompte).get();
		//NB: plantage si pas de @JsonIgnore et généralement sans_DTO = très mauvaise pratique
	}
*/


	private ServiceCompte serviceCompte;

	@Autowired
	public CompteRestCtrl(ServiceCompte serviceCompte) {
		this.serviceCompte = serviceCompte;
	}

	//Get By ID
	//V1 avec DTO et V3 (avec automatisme ExceptionHandler)
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1 ou 2
	@GetMapping("/{id}")
	public Compte getCompteById(@PathVariable("id") long numeroCompte) {
		return serviceCompte.searchById(numeroCompte);
		//NB: l'objet retourné sera automatiquement converti au format json
	}


/*
	//V2 avec ResponseEntity<?> mais sans ExceptionHandler
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1 ou 2
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
        try {
            Compte compte = serviceCompte.searchById(numeroCompte);
			return new ResponseEntity<Compte>(compte, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
			return new ResponseEntity<MessageDto>(new MessageDto(e.getMessage()), HttpStatus.NOT_FOUND);
        }
	}
*/

	//GET Multiple
	//http://localhost:8181/appliSpring/rest/api-bank/v1/comptes
	//http://localhost:8181/appliSpring/rest/api-bank/v1/comptes?soldeMini=50
	//....
	@GetMapping()
	public List<Compte> getComptesByCriteria(
			@RequestParam(value="soldeMini",required=false) Double soldeMini) {
		if(soldeMini==null)
			return serviceCompte.searchAll();
		else
			return serviceCompte.searchByIdWithMinimumBalance(soldeMini);
	}

	//appelé en mode POST
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/v1/comptes
	//avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	@PostMapping("")
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	public ResponseEntity<?> postCompte(@Valid @RequestBody CompteToCreate compte) {
		Compte compteSauvegarde = serviceCompte.create(compte);  //avec numero auto_incrémenté
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(compteSauvegarde.getNumero()).toUri();
		//return ResponseEntity.created(location).build(); //return 201/CREATED , no body but URI to find added account
		return ResponseEntity.created(location).body(compteSauvegarde);//return 201/CREATED with account  AND with URI to find added account
       /* ou bien encore
		return ResponseEntity.ok()
				.headers(responseHeadersWithLocation)
				.body(compteSauvegarde); //avec numero auto_incrémenté
		*/
	}

	//appelé en mode PUT
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1
	//avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	public ResponseEntity<Compte> putCompte(@RequestBody Compte compte, @PathVariable("id") Long idToUpdate) {
		compte.setNumero(idToUpdate);
		Compte compteMisAJour = serviceCompte.update(compte);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204 : OK sans aucun message dans partie body
		//execption handler may return NOT_FOUND or INTERNAL_SERVER_ERROR
	}

	//http://localhost:8181/appliSpring/rest/api-bank/v1/comptes/1 ou 2  (DELETE)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('SCOPE_resource.delete')")
	public ResponseEntity<?> deleteCompteById(@PathVariable("id") Long id) {
		serviceCompte.removeById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //NO_CONTENT = OK mais sans message
		//return ResponseEntity.ok(new MessageDto("compte with id=" + id + " successfully deleted")); //200/OK + message
		//execption handler may return NOT_FOUND or INTERNAL_SERVER_ERROR
	}
}




