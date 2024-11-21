package tp.appliSpring.web.rest;

import java.util.ArrayList;
import java.util.List;

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
import tp.appliSpring.dto.CompteDto;

@RestController //@Component de type controller d'api rest
@RequestMapping(value="/rest/api-bank/comptes" , headers="Accept=application/json")
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
		
			Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
			return GenericMapper.MAPPER.map(compteEntity, CompteDto.class);
			//NB: l'objet retourné sera automatiquement converti au format json
		}


	
     /*
	//V3 avec ResponseEntity<?> mais sans ExceptionHandler
	//declencher en mode GET avec
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
				try {
					Compte compteEntity = serviceCompte.rechercherCompte( numeroCompte);
					return new ResponseEntity<CompteDto>(GenericMapper.MAPPER.map(compteEntity, CompteDto.class) , HttpStatus.OK);
				} catch (NotFoundException e) {
					//e.printStackTrace();
					System.err.println(e.getMessage());
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
	}
   */
	
	//En GET
	//http://localhost:8181/appliSpring/rest/api-bank/comptes
	//http://localhost:8181/appliSpring/rest/api-bank/comptes?soldeMini=50
	//http://localhost:8181/appliSpring/rest/api-bank/comptes?soldeMini=50&critere2=val2&critere3=val3
	//A CODER EN TP
	@GetMapping()
	public List<CompteDto> getCompteDtoByCriteria(
			@RequestParam(value="soldeMini",required=false) Double soldeMini) {
		List<Compte> listeComptes = null;
		if(soldeMini==null)
			listeComptes = serviceCompte.rechercherTousLesComptes();
		else
			listeComptes = serviceCompte.rechercherComptesAvecSoldeMini(soldeMini);
		return GenericMapper.MAPPER.map(listeComptes, CompteDto.class);
	}
	
	//appelé en mode POST
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/comptes
	//avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	//A CODER EN
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	@PostMapping()
	public CompteDto postCompteDto(@Valid @RequestBody CompteDto compteDto){
		Compte compteEntity = GenericMapper.MAPPER.map(compteDto,Compte.class);
		Compte compteSauvegarde = serviceCompte.sauvegarderCompte(compteEntity);
		//on peut par exemple retourner en retour le compte sauvegardé
		// avec la clef primaire auto-incrémentée:
		return GenericMapper.MAPPER.map(compteSauvegarde,CompteDto.class);
	}

	
	//appelé en mode PUT
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/comptes
	//ou bien avec url = http://localhost:8181/appliSpring/rest/api-bank/comptes/1
	//avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
	//A CODER EN TP
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	@PutMapping({ "" , "/{id}" })
	public CompteDto putCompteDto(@Valid @RequestBody CompteDto compteDto,
								  @PathVariable(value = "id",required = false) Long numeroCompte){
		if(numeroCompte!=null) compteDto.setNumero(numeroCompte);
		Compte compteEntity = GenericMapper.MAPPER.map(compteDto,Compte.class);
		Compte compteSauvegarde = serviceCompte.updateCompte(compteEntity);
		//on peut par exemple retourner en retour le compte sauvegardé
		return GenericMapper.MAPPER.map(compteSauvegarde,CompteDto.class);
	}

	
	//http://localhost:8181/appliSpring/rest/api-bank/comptes/1 ou 2
	//A CODER EN
	@PreAuthorize("hasAuthority('SCOPE_resource.delete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCompteById(@PathVariable("id") long numeroCompte) {
		serviceCompte.deleteCompte(numeroCompte);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	
	
	public CompteRestCtrl() {
	}

}
