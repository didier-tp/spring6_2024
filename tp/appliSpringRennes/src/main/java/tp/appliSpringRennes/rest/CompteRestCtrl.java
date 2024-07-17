package tp.appliSpringRennes.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tp.appliSpringRennes.entity.Compte;
import tp.appliSpringRennes.service.ServiceCompte;

@RestController
@RequestMapping(value="/rest/api-bank/comptes" , headers="Accept=application/json")
public class CompteRestCtrl {

    private ServiceCompte serviceCompte;

    public CompteRestCtrl(ServiceCompte serviceCompte) {
        this.serviceCompte = serviceCompte;
    }

    //RECHERCHE UNIQUE selon RESOURCE-ID:
    //URL de déclenchement: .../rest/api-bank/comptes/1
    @GetMapping("/{numero}" )
    public Compte getCompteByNum(@PathVariable("numero") Long numero) {
        return serviceCompte.searchById(numero);
    }

}
