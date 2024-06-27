package tp.appliSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tp.appliSpringBoot.entity.Compte;

import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    //principales méthodes héritées:
    //.save() , .findById() , .deleteById() , .... ***

    List<Compte> findBySoldeGreaterThanEqual(double soldeMini); //selon convention de nom d'une méthode de recherche

    @Query("SELECT c FROM Compte c WHERE c.label like ?1")
    List<Compte> findByLabelLikeThis(String format); //avec requete personnalisée

}
