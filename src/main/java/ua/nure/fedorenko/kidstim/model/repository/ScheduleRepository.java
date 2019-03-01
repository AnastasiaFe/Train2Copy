package ua.nure.fedorenko.kidstim.model.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, String> {
    @Query("SELECT s FROM Schedule s where s.child.id=:childId and finishDate>=CURRENT_DATE")
    Schedule findActiveScheduleForChild(@Param("childId")String childId);
}
