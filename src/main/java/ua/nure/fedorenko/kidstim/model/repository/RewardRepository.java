package ua.nure.fedorenko.kidstim.model.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Parent;
import ua.nure.fedorenko.kidstim.model.entity.Reward;

import java.util.List;

@Repository
public interface RewardRepository extends CrudRepository<Reward, String> {

    public List<Reward> findByParent(@Param("parent") Parent parent);

    @Query("SELECT r FROM Reward r where :child in elements (r.children)")
    public List<Reward> getRewardsByChild(@Param("child") Child child);

}
