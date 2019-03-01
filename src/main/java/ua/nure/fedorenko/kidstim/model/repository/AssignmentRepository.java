package ua.nure.fedorenko.kidstim.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Assignment;
import ua.nure.fedorenko.kidstim.model.entity.Schedule;

import java.util.List;

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, String> {
    @Query("SELECT s FROM Assignment s where s.day.id=:day")
    Assignment findByDay(@Param("day") String day);

    List<Assignment> findBySchedule(@Param("schedule") Schedule schedule);
}
