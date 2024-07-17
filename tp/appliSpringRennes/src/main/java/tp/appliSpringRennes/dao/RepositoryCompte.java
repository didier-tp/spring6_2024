package tp.appliSpringRennes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tp.appliSpringRennes.entity.Compte;

import java.util.List;

public interface RepositoryCompte extends JpaRepository<Compte,Long> {
    /*
    principales méthodes héritées: .save() , .findById() , .deleteById()
     */

    //exemple de méthode respectant les conventions de nommage
    //et avec requête codée automatiquement:
    List<Compte> findBySoldeGreaterThanEqual(double soldeMini);

    //exemple de méthode ne respectant pas les conventions de nommage
    //et à coder via @Query() ou @NamedQuery() sur @Entity
    @Query("SELECT c FROM Compte c WHERE c.solde < ?1")
    List<Compte> findBySoldePlusPetitQue(double soldeMaxi);

    //recherche et retourne un compte avec les operations rattachées:
    @Query("SELECT c FROM Compte c LEFT JOIN FETCH c.operations o WHERE c.numero = ?1")
    Compte findByIdWithOperations(long numCompte);
}
