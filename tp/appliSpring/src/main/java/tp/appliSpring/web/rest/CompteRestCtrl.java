package tp.appliSpring.web.rest;

import java.util.ArrayList;
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
import tp.appliSpring.converter.GenericMapper;
import tp.appliSpring.core.entity.Compte;
import tp.appliSpring.core.service.ServiceCompte;
import tp.appliSpring.dto.CompteDto;

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
	//http://localhost:8181/appliSpring/rest/api-bank/compte
	//http://localhost:8181/appliSpring/rest/api-bank/compte?soldeMini=50
	//http://localhost:8181/appliSpring/rest/api-bank/compte?soldeMini=50&critere2=val2&critere3=val3
	@GetMapping("")
	public List<CompteDto> getComptesByCriteria(@RequestParam(value="soldeMini",required=false) Double soldeMini,
			                                    @RequestParam(value="critere2",required=false) String critere2) {
		List<Compte> listeCompteEntity = new ArrayList<>();
		if(soldeMini!=null)
			listeCompteEntity=serviceCompte.rechercherComptesAvecSoldeMini(soldeMini);
		if(soldeMini==null)
			listeCompteEntity=serviceCompte.rechercherTousLesComptes();
		return GenericMapper.MAPPER.map(listeCompteEntity, CompteDto.class);
	}
	
	//appelé en mode POST
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/compte
	//avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	@PostMapping("")
	public CompteDto postCompte(@Valid @RequestBody CompteDto compteDto) {
		Compte compteEntity = GenericMapper.MAPPER.map(compteDto, Compte.class);
		Compte compteSauvegarde = serviceCompte.sauvegarderCompte(compteEntity);  //avec numero auto_incrémenté
		compteDto = GenericMapper.MAPPER.map(compteSauvegarde, CompteDto.class);
		return compteDto; //avec numero auto_incrémenté
	}
	
	//appelé en mode PUT
	//avec url = http://localhost:8181/appliSpring/rest/api-bank/compte
	//ou bien avec url = http://localhost:8181/appliSpring/rest/api-bank/compte/1
	//avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
	@PutMapping("")
	public CompteDto putCompte( @RequestBody CompteDto compteDto) {
			Compte compteEntity = GenericMapper.MAPPER.map(compteDto, Compte.class);
			Compte compteMisAJour = serviceCompte.updateCompte(compteEntity);  
			compteDto = GenericMapper.MAPPER.map(compteMisAJour, CompteDto.class);
			return compteDto; //on pourrait simplement retourner ResponseEntity<>(avec status OK)
		}
	
	//http://localhost:8181/appliSpring/rest/api-bank/compte/1 ou 2 
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCompteById(@PathVariable("id") long numeroCompte) {
		serviceCompte.deleteCompte( numeroCompte);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); //NO_CONTENT = OK mais sans message
	}
	
	
	
	public CompteRestCtrl() {
	}

}
