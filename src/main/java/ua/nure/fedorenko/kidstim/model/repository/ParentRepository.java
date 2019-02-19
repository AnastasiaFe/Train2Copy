package ua.nure.fedorenko.kidstim.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Parent;

@Repository
public interface ParentRepository extends CrudRepository<Parent,String> {

    Parent findByEmail(@Param("email") String email);

}
