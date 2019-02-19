package ua.nure.fedorenko.kidstim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.fedorenko.kidstim.model.entity.Child;
import ua.nure.fedorenko.kidstim.model.entity.Reward;
import ua.nure.fedorenko.kidstim.service.ChildService;
import ua.nure.fedorenko.kidstim.service.RewardService;


import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class RewardRestController {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private ChildService childService;

    @PutMapping(value = "/updateReward")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateReward(@RequestBody Reward reward) {
        rewardService.saveReward(reward);
    }

    @PostMapping(value = "/saveReward")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addReward(@RequestBody Reward reward) {
        rewardService.saveReward(reward);
    }

    @DeleteMapping(value = "/deleteReward")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteReward(@NotNull @RequestParam("id") String id) {
        rewardService.deleteReward(id);
    }


    @GetMapping(value = "/reward")
    public ResponseEntity getRewardById(@NotNull @RequestParam("id") String id) {
        Reward reward = rewardService.getRewardById(id);
        if (reward == null) {
            return new ResponseEntity("No reward found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reward, HttpStatus.OK);
    }

    @GetMapping(value = "/rewardsByParent")
    public ResponseEntity<List<Reward>> getRewardsByParent(@NotNull @RequestParam("id") String parentId) {
        List<Reward> rewards = rewardService.getRewardsByParent(parentId);
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }

    @GetMapping(value = "/rewardsByChild")
    public ResponseEntity<List<Reward>> getRewardsByChild(@NotNull @RequestParam("id") String childId) {
        Child child = childService.getChildById(childId);
        List<Reward> rewards = rewardService.getRewardsByChild(child);
        return new ResponseEntity<>(rewards, HttpStatus.OK);
    }
}
