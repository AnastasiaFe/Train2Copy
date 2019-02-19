package ua.nure.fedorenko.kidstim.model.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.model.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, String> {


    public List<Task> findByParent(@Param("parent") Parent parent);

    @Query("SELECT t FROM Task t where :child in elements (t.children)")
    public List<Task> getTasksByChild(Child child);
}
