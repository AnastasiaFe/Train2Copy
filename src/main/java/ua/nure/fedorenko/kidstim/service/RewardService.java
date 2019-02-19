package ua.nure.fedorenko.kidstim.service;

import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Reward;

import java.util.List;

public interface RewardService {

    Reward getRewardById(String id);

    void saveReward(Reward reward);

    void deleteReward(String id);

    List<Reward> getRewardsByParent(String parent);

    List<Reward> getRewardsByChild(Child child);
}
