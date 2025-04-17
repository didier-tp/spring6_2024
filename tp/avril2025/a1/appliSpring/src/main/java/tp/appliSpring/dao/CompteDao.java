package tp.appliSpring.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.appliSpring.entity.Compte;

public interface CompteDao extends JpaRepository<Compte,Long>{

}
