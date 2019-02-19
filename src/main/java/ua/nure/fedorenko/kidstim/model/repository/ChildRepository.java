package ua.nure.fedorenko.kidstim.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Child;

@Repository
public interface ChildRepository extends CrudRepository<Child, String> {

    Child findByEmail(@Param("email") String email);
}
