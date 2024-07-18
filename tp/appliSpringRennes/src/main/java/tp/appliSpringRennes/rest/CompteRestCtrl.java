package tp.appliSpringRennes.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.appliSpringRennes.converter.MyConverter;
import tp.appliSpringRennes.dto.CompteDto;
import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.service.ServiceCompte;

import java.util.List;

@RestController
@RequestMapping(value="/rest/api-bank/comptes" , headers="Accept=application/json")
public class CompteRestCtrl {

    @Autowired
    private MyConverter myConverter;

    private ServiceCompte serviceCompte;

    public CompteRestCtrl(ServiceCompte serviceCompte) {
        this.serviceCompte = serviceCompte;
    }

    //RECHERCHE UNIQUE selon RESOURCE-ID:
    //URL de déclenchement: .../rest/api-bank/comptes/1
    //avec gestion automatique des exceptions via le "ExceptionHandler"
    @GetMapping("/{numero}" )
    public CompteDto getCompteByNum(@PathVariable("numero") Long numero) {
        Compte compte = serviceCompte.searchById(numero);
        return myConverter.compteToCompteDto(compte);
    }


    /*
    @GetMapping("/{numero}" )
    public ResponseEntity<CompteDto> getCompteByNum(
            @PathVariable("numero") Long numero) {
        try {
            Compte compte = serviceCompte.searchById(numero);
            return new ResponseEntity<>(myConverter.compteToCompteDto(compte),
                    HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    */


    //RECHERCHE MULTIPLE :
    //URL de déclenchement: comptes
    //ou comptes?soldeMini=0
    @GetMapping("" )
    public List<CompteDto> getComptesByCriteria(
            @RequestParam(value="soldeMini",required=false) Double soldeMini) {
        if(soldeMini==null)
            return myConverter.compteListToCompteDtoList(
                    serviceCompte.getAllComptes());
        else
            return myConverter.compteListToCompteDtoList(
                    serviceCompte.getComptesBySoldeMini(soldeMini));
    }

    //à appeler en mode POST avec { "label" : "ccc" , "solde" : 60 }
    //en retour  { "numero" : 5 , "label" : "ccc" , "solde" : 60 }
    @PostMapping
    public ResponseEntity<CompteDto> postCompteDto(@RequestBody CompteDto compteDto){
        try {
            Compte compte = myConverter.compteDtoToCompte(compteDto);
            compte = serviceCompte.saveOrUpdate(compte);
            //variantes : retourner 201/CREATED avec Location: "/comptes/id"
            //ou bien retourner 201/CREATED avec compte comporte id
            // (ou bien un mixte des 2)
            compteDto.setNumero(compte.getNumero());
            ResponseEntity<CompteDto>  response ;
            response = new ResponseEntity<>(compteDto,HttpStatus.CREATED);
            /*response = ResponseEntity.status(HttpStatus.CREATED)
                      .header("Location","/comptes/"
                              +compte.getNumero()).build();*/
            return response;
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
