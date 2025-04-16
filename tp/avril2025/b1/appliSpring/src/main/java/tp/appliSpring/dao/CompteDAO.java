package tp.appliSpring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
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

}

/*
 si spring.data.jpa.repositories.enabled=true
 alors la classe d'implémentation s'appuyant sur EntityManager
 sera codée/générée automatiquement
 */
