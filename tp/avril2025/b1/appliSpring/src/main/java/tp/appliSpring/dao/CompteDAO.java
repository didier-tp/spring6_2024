package tp.appliSpring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tp.appliSpring.entity.Compte;

/*
 interface de DAO/Repository
 DAO = Data Access Object avec méthodes CRUD
 automatiquement héritées de JpaRepository / CrudRepository
 */

public interface CompteDAO extends JpaRepository<Compte,Long>{
	/* principales méthodes héritées:
	 * .save(compte)
	 * .findById(numero)
	 * .deleteById(numero)
	 * .findAll()
	 */

	//nouvelles méthodes de recherche:
	//SELECT automatique selon convention de nom de méthode
	List<Compte> findBySoldeGreaterThanEqual(double soldeMini);
	
	//requête personnalisée au format JPAQL:
	@Query("SELECT c FROM Compte c WHERE c.solde >= ?1 ")
	List<Compte> findSelonSoldeMini(double soldeMini);
}

/*
 si spring.data.jpa.repositories.enabled=true
 alors la classe d'implémentation s'appuyant sur EntityManager
 sera codée/générée automatiquement
 */
