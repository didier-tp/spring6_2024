package tp.appliSpring.web.rest;

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

import jakarta.validation.Valid;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.CompteDto;
import tp.appliSpring.dto.MessageDto;

@RestController //@Component de type controller d'api rest
@RequestMapping(value="/rest/api-bank/compte" , headers="Accept=application/json")
public class CompteRestCtrl {
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	/*
	//V1 sans DTO
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	@GetMapping("/{id}")
	public Compte getCompteById(@PathVariable("id") long numeroCompte) {
		System.out.println("getCompteById() appelee avec numeroCompte="+numeroCompte);
		Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
		System.out.println("getCompteById() retournant compteEntity="+compteEntity);
		return compteEntity;
		//NB: l'objet retourné sera automatiquement converti au format json
	}
    */
	
	
	//V2 avec DTO et V4 (avec automatisme ExceptionHandler)
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	@GetMapping("/{id}")
	public CompteDto getCompteById(@PathVariable("id") long numeroCompte) {
		
			return serviceCompte.rechercherCompte( numeroCompte);
		
			//NB: l'objet retourné sera automatiquement converti au format json
		}
   
	
	/*
	//V3 avec ResponseEntity<?> mais sans ExceptionHandler
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
				try {
					CompteDto compteDto = serviceCompte.rechercherCompte( numeroCompte);
					return new ResponseEntity<CompteDto>(compteDto , HttpStatus.OK);
				} catch (NotFoundException e) {
					//e.printStackTrace();
					System.err.println(e.getMessage());
					return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND , "compte inexistant"),
							                    HttpStatus.NOT_FOUND);
				}
	}
    */
	
	//En GET
	//http://localhost:8181/appliSpring/rest/api-bank/compte
	//http://localhost:8181/appliSpring/rest/api-bank/compte?soldeMini=50
	//http://localhost:8181/appliSpring/rest/api-bank/compte?soldeMini=50&critere2=val2&critere3=val3
	//A CODER EN TP
	
	@GetMapping("")
	public List<CompteDto> getComptesByCriteria(@RequestParam(name="soldeMini",required=false) Double soldeMini) {
		if(soldeMini==null)
			return serviceCompte.rechercherTousLesComptes();
		else
			return serviceCompte.rechercherComptesAvecSoldeMini(soldeMini);
	}
	
	//appelé en mode POST
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/compte
	//avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	//A CODER EN TP
	
	@PostMapping("" )
	ResponseEntity<CompteDto> postCompte(@RequestBody @Valid CompteDto compteDto) {
		System.out.println("account to insert:" + compteDto);
		CompteDto compteSauvegarde =  serviceCompte.sauvegarderCompte(compteDto);
		return new ResponseEntity<CompteDto>(compteSauvegarde,HttpStatus.CREATED);
	}
	
	//appelé en mode PUT
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/compte
	//ou bien avec url = http://localhost:8181/appliSpring/rest/api-bank/compte/1
	//avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
	//A CODER EN TP
	@PutMapping("" )
	CompteDto putCompte(@RequestBody CompteDto compteDto) {
		System.out.println("account to update:" + compteDto);
		return serviceCompte.updateCompte(compteDto);
	}
	
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	//A CODER EN TP
	@DeleteMapping("/{id}")
	public ResponseEntity deleteCompteById(@PathVariable("id") long numeroCompte) {
		
			serviceCompte.deleteCompte( numeroCompte);
		
			//NB: en cas de compte pas trouvé et impossible à supprimer
			//l'exception notFoundException sera automatiquement transformée en 404/...
			//via le exceptionHandler
			
			//code optimiste déclenché que si pas exception avant:
			return new ResponseEntity<>(new MessageDto("compte bien supprimé avec numero="+numeroCompte), HttpStatus.OK);
			//return new ResponseEntity<>(HttpStatus.NO_CONTENT); //code 204 , variante de 200_OK)
		}
	
	
	
	public CompteRestCtrl() {
	}

}
