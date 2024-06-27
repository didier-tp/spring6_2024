package tp.appliSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.appliSpringBoot.entity.Compte;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    //principales méthodes héritées:
    //.save() , .findById() , .deleteById() , .... ***
}
