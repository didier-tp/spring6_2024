package tp.appliSpring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

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
}
