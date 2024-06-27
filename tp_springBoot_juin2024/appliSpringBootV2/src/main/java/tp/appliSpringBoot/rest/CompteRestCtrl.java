package tp.appliSpringBoot.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tp.appliSpringBoot.entity.Compte;
import tp.appliSpringBoot.repository.CompteRepository;

@RestController
@RequestMapping(value="/rest/api-bank/compte" , headers="Accept=application/json")
@CrossOrigin(origins = "*" , methods = { RequestMethod.GET , RequestMethod.POST})
public class CompteRestCtrl {
	
	/*
	//Pour version sophistiquee/complexe:
	@Autowired
	private ServiceCompteWithDto serviceCompte;
	*/
	
	//Pour version ultra simplifiée:
	@Autowired
	private CompteRepository compteRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCompteById(@PathVariable("id") long numeroCompte) {
		return this.compteRepository.findById(numeroCompte)
		        .map(compte-> ResponseEntity.ok().body(compte) )
		        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}
	
	
	//exemple de fin d'URL: ./rest/api-bank/compte
	//                      ./rest/api-bank/compte?soldeMini=0
	@GetMapping("")
	public List<Compte> getComptesByCriteria(
			 @RequestParam(value="soldeMini",required=false) Double soldeMini) {
		if(soldeMini!=null)
			return compteRepository.findBySoldeGreaterThanEqual(soldeMini);
		else
			return compteRepository.findAll();
	}
	
	//appelé en mode POST
	//avec url = .../rest/api-bank/compte
	//avec dans la partie "body" de la requête
	// { "numero" : null , "label" : "comptequiVaBien" , "solde" : 50.0 }
	@PostMapping("")
	//@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasAuthority('SCOPE_resource.write')") 
	public Compte postCompte( @RequestBody Compte compte) {
			Compte compteSauvegarde = compteRepository.save(compte);  //avec numero auto_incrémenté
			return compteSauvegarde; //avec numero auto_incrémenté
	}
		
	//appelé en mode PUT
	//avec url = .../rest/api-bank/compte
	//ou bien avec url = .../rest/api-bank/compte/1
	//avec dans la partie "body" de la requête
	// { "numero" : 1 , "label" : "libelleModifie" , "solde" : 120.0  }
	@PutMapping({"","/{id}"})
	//@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasAuthority('SCOPE_resource.write')")
	public ResponseEntity<Compte> putCompte( @RequestBody Compte compte, 
								@PathVariable(name="id",required = false) Long numeroCompte) {
		    //si l'id du compte à mettre à jour est passé en fin d'url, on répercute cela dans le dto
			if(numeroCompte!=null) {
				compte.setNumero(numeroCompte);
			}
			else {
				//sinon , on considère que l'appelant a déjà  placé l'id dans la partie json/body
				numeroCompte=compte.getNumero();
			}
			if(!compteRepository.existsById(numeroCompte))
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			
			Compte compteMisAJour = compteRepository.save(compte); 
			return  ResponseEntity.ok().body(compteMisAJour); //on pourrait simplement retourner ResponseEntity<>(avec status OK)
	}
		
	//.../rest/api-bank/compte/1 ou 2 
	@DeleteMapping("/{id}")
	//@PreAuthorize("hasRole('ROLE_CUSTOMER') || hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasAuthority('SCOPE_resource.delete')")
	public ResponseEntity<?> deleteCompteById(@PathVariable("id") Long numeroCompte) {
		compteRepository.deleteById( numeroCompte);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); //NO_CONTENT = OK mais sans message
	}
    

}
