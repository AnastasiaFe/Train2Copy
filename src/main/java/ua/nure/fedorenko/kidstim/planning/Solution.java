package ua.nure.fedorenko.kidstim.planning;

import ua.nure.fedorenko.kidstim.model.entity.Task;

import java.util.List;

public class Solution {
    // list of items to put in the bag to have the maximal value
    private List<Task> items;
    // maximal value possible
    private int value;

    public Solution(List<Task> items, int value) {
        this.items = items;
        this.value = value;
    }

    public List<Task> getItems() {
        return items;
    }

    public int getValue() {
        return value;
    }
}
