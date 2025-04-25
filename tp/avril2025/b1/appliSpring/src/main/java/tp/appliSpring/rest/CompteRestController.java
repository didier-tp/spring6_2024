package tp.appliSpring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.service.ServiceCompte;

@RestController //composant spring pour api REST
@RequestMapping(value="/rest/bank-api/v1/comptes",
                headers="Accept=application/json")
public class CompteRestController {
	
	@Autowired
	private ServiceCompte serviceCompte;
	
	//URL de déclenchement:
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@GetMapping("/{numero}") 
	public ResponseEntity<Compte> getCompteByNum(@PathVariable("numero")long numero) {
		try {
			return new ResponseEntity<Compte>(serviceCompte.searchByNumero(numero),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);//404
		}
	}
	/*
	@GetMapping("/{numero}") 
	public Compte getCompteByNum(@PathVariable("numero")long numero) {
		return serviceCompte.searchByNumero(numero);
	}*/
	
	//URL de déclenchement:
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes?soldeMini=60
	@GetMapping()  
	public List<Compte> getComptesByCriteria(
			@RequestParam(value="soldeMini",required=false) Double soldeMini
			) {
		if(soldeMini!=null)
			return serviceCompte.searchSelonSoldeMini(soldeMini);
		/*else*/
		return serviceCompte.searchAll();
	}

}
