package tp.appliSpringBoot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tp.appliSpringBoot.entity.Compte;
import tp.appliSpringBoot.repository.CompteRepository;

@RestController
@RequestMapping(value="/rest/api-bank/compte" , headers="Accept=application/json")
public class CompteRestCtrl {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/{numero}" )
    public Compte getCompteByNum(@PathVariable("numero") Long numero) {
        return compteRepository.findById(numero).get();  //V1
    }

}
