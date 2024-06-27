package tp.appliSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.appliSpringBoot.entity.Compte;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    //principales méthodes héritées:
    //.save() , .findById() , .deleteById() , .... ***

    List<Compte> findBySoldeGreaterThanEqual(double soldeMini); //selon convention de nom d'une méthode de recherche
}
