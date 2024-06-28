package tp.appliSpringBoot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.appliSpringBoot.entity.Compte;
import tp.appliSpringBoot.repository.CompteRepository;

import java.util.List;

@RestController
@RequestMapping(value="/rest/api-bank/compte" , headers="Accept=application/json")
public class CompteRestCtrl {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/{numero}" )
    public Compte getCompteByNum(@PathVariable("numero") Long numero) {
        return compteRepository.findById(numero).get();  //V1
    }

    /*
    @GetMapping("/{numero}" )
    public ResponseEntity<Compte> getCompteByNum(@PathVariable("numero") Long numero) {
        if(compteRepository.findById(numero).isEmpty())
//          return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        else
            return ResponseEntity.ok(compteRepository.findById(numero).get());
    }
    */

    //api-bank/compte?soldeMini=200 ou api-bank/compte
    @GetMapping("" )
    public List<Compte> getComptesByCriteria(@RequestParam(name="soldeMini", required = false) Double soldeMini) {
       if(soldeMini == null)
           return compteRepository.findAll();
       else
           return compteRepository.findBySoldeGreaterThanEqual(soldeMini);
    }

}
