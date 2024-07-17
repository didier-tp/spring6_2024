package tp.appliSpringRennes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tp.appliSpringRennes.entity.Operation;

public interface RepositoryOperation extends JpaRepository<Operation,Long> {
}
