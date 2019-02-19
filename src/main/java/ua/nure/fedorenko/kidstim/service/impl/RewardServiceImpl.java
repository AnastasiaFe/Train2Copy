package ua.nure.fedorenko.kidstim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Reward;
import ua.nure.fedorenko.kidstim.model.repository.RewardRepository;
import ua.nure.fedorenko.kidstim.service.ParentService;
import ua.nure.fedorenko.kidstim.service.RewardService;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private ParentService parentService;

    @Override
    public Reward getRewardById(String id) {
        Optional<Reward> reward = rewardRepository.findById(id);
        return reward.orElse(null);
    }

    @Override
    public void saveReward(Reward reward) {
        rewardRepository.save(reward);
    }

    @Override
    public void deleteReward(String id) {
        rewardRepository.delete(getRewardById(id));
    }

    @Override
    public List<Reward> getRewardsByParent(String parentId) {
        return rewardRepository.findByParent(parentService.getParentById(parentId));
    }

    @Override
    public List<Reward> getRewardsByChild(Child child) {
        return rewardRepository.getRewardsByChild(child);
    }
}
