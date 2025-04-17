package tp.appliSpring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tp.appliSpring.entity.Compte;

/*
 DAO (alias Repository) : Data Access Object
 */

public interface CompteDao extends JpaRepository<Compte,Long>{
     /*
      principales méthodes héritées:
      .save(compte)
      .findById(numero)
      .deleteById(numero)
      .findAll()
      */
	
	//méthode de recherche qui respecte des conventions de nom de méthode findBy...GreaterThanEqual
	List<Compte> findBySoldeGreaterThanEqual(double soldeMini);
	
	@Query("SELECT c FROM Compte c WHERE c.solde >= ?1")
	List<Compte> findSelonSoldeMini(double soldeMini);
	
}
