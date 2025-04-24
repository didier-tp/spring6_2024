package tp.appliSpring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tp.appliSpring.entity.Compte;
import tp.appliSpring.service.ServiceCompte;

@RestController //composant spring pour WS REST
@RequestMapping(value="/rest/bank-api/v1/comptes" ,
               headers="Accept=application/json")
public class CompteRestController {

	@Autowired
	private ServiceCompte serviceCompte;
	
	//URL de déclenchement:
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@GetMapping("/{numero}" )
	public ResponseEntity<Compte> getCompteByNum(@PathVariable("numero") Long numero) {
		try {
			Compte c = serviceCompte.searchById(numero);
			return new ResponseEntity<Compte>(c,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	@GetMapping("/{numero}" )
	public Compte getCompteByNum(@PathVariable("numero") Long numero) {
		return serviceCompte.searchById(numero);
	}
	*/
	
	
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes/1
	@DeleteMapping("/{numero}" )
	public ResponseEntity<?> deleteCompteByNum(@PathVariable("numero") Long numero) {
			try {
				serviceCompte.removeById(numero);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204: OK mais sans détails/ sans message
			} catch (Exception e) {
				return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);
			}
	}
	
	//URL de déclenchement:
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes
	//http://localhost:8181/appliSpring/rest/bank-api/v1/comptes?soldeMini=60
	@GetMapping()
	public List<Compte> getComptesByCriteria(
			@RequestParam(value="soldeMini",required=false)Double soldeMini){
		if(soldeMini!=null)
			return serviceCompte.searchBySoldeMini(soldeMini);
		/*else*/
		return serviceCompte.searchAll();
	}
	
}
