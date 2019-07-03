package ua.nure.fedorenko.kidstim.model.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.model.entity.Task;
import ua.nure.fedorenko.kidstim.model.entity.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, String> {


    List<Task> findByParent(@Param("parent") Parent parent);

    @Query("SELECT t FROM Task t where :child in elements (t.children)")
    List<Task> getTasksByChild(@Param("child") Child child);

    List<Task> findByStatus(@Param("status") TaskStatus taskStatus);

}
