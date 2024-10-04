package tp.appliSpring.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import tp.appliSpring.converter.GenericMapper;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.exception.NotFoundException;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.ApiError;
import tp.appliSpring.dto.CompteDto;

@RestController //@Component de type controller d'api rest
@RequestMapping(value="/rest/api-bank/comptes" , headers="Accept=application/json")
public class CompteRestCtrl {
	
	@Autowired
	private ServiceCompte serviceCompte;
	
    /*
	//V1 sans DTO
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/comptes/1 ou 2
	@GetMapping("/{id}")
	public Compte getCompteById(@PathVariable("id") long numeroCompte) {
		System.out.println("getCompteById() appelee avec numeroCompte="+numeroCompte);
		Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
		System.out.println("getCompteById() retournant compteEntity="+compteEntity);
		return compteEntity;
		//NB: l'objet retourné sera automatiquement converti au format json
	}
   */
	
   /*
	//V2 avec DTO et V4 (avec automatisme ExceptionHandler)
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/comptes/1 ou 2
	@GetMapping("/{id}")
	public CompteDto getCompteById(@PathVariable("id") long numeroCompte) {
		
			Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
			return GenericMapper.MAPPER.map(compteEntity, CompteDto.class);
			//NB: l'objet retourné sera automatiquement converti au format json
		}
     */
	
    /*
	//V3 avec ResponseEntity<?> mais sans ExceptionHandler
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/comptes/1 ou 2
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
				try {
					Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
					return new ResponseEntity<CompteDto>(
							  GenericMapper.MAPPER.map(compteEntity, CompteDto.class) ,
							  HttpStatus.OK);
				} catch (NotFoundException e) {
					//e.printStackTrace();
					System.err.println(e.getMessage());
					return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND,
							                        "compte pas trouve pour numero="+numeroCompte) ,
							                HttpStatus.NOT_FOUND);
				}
	}
     */

	//Version 3bis avec optional
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
		Optional<Compte> compteOptional = serviceCompte.rechercherCompteOptional(numeroCompte);
		/*
		return compteOptional.map( ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		*/
		return ResponseEntity.of(compteOptional); //ecriture équivalente plus compacte (sans message)
	}

	//En GET
	//RECHERCHE MULTIPLE :
	//URL de déclenchement:http://localhost:8181/appliSpring/rest/api-bank/comptes
	//	ou http://localhost:8181/appliSpring/rest/api-bank/comptes?soldeMini=50
	@GetMapping("" )
	public List<CompteDto> getComptesByCriteria(
			@RequestParam(value="soldeMini",required=false) Double soldeMini
	       /* @RequestParam(value="soldeMini",defaultValue = "0") double soldeMini */) {
		List<Compte> listeCompteEntity = null;
		if(soldeMini==null)
			listeCompteEntity = serviceCompte.rechercherTousLesComptes();
		else {
			listeCompteEntity = serviceCompte.rechercherComptesAvecSoldeMini(soldeMini);
		}
		return GenericMapper.MAPPER.map(listeCompteEntity, CompteDto.class);
	}

	
	//appelé en mode POST
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/comptes
	//avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	//A CODER EN TP

	@PostMapping()
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	public ResponseEntity<CompteDto> postCompte(@Valid @RequestBody CompteDto compteDto){
          Compte compteEntity= GenericMapper.MAPPER.map(compteDto , Compte.class);
		  Compte compteSauvegarde = serviceCompte.sauvegarderCompte(compteEntity);
		  CompteDto compteSauvegardeDto = GenericMapper.MAPPER.map(compteSauvegarde , CompteDto.class);
		  return new ResponseEntity<>(compteSauvegardeDto , HttpStatus.CREATED);
	}
	
	//appelé en mode PUT
	//avec  url = http://localhost:8181/appliSpring/rest/api-bank/comptes/1
	//avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
	//A CODER EN TP
	@PutMapping("/{numero}")
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	public ResponseEntity<CompteDto> putCompte(@PathVariable("numero") long numCpt ,@RequestBody CompteDto compteDto){
		compteDto.setNumero(numCpt);
		Compte compteEntity= GenericMapper.MAPPER.map(compteDto , Compte.class);
		Compte compteSauvegarde = serviceCompte.updateCompte(compteEntity);
		//en cas de compte inexistant, le exception Handler retourne automatiquement not_found
		CompteDto compteSauvegardeDto = GenericMapper.MAPPER.map(compteSauvegarde , CompteDto.class);
		return new ResponseEntity<>(compteSauvegardeDto , HttpStatus.OK);
		//return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	//A CODER EN TP
	@DeleteMapping("/{numero}")
	@PreAuthorize("hasAuthority('SCOPE_resource.delete')")
	public ResponseEntity<?> deleteCompte(@PathVariable("numero") long numCpt){
		serviceCompte.deleteCompte(numCpt);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		//en cas de compte inexistant, le exception Handler retourne automatiquement not_found
	}
	
	
	
	public CompteRestCtrl() {
	}

}
