package ua.nure.fedorenko.kidstim.model.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Schedule {

    private String id;
    private Map<Day, List<Task>> assignments;
    private String childId;

    public Schedule() {

    }

    public Schedule(Map<Day, List<Task>> assignments) {
        this.assignments = assignments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Day, List<Task>> getAssignments() {
        return assignments;
    }

    public void setAssignments(Map<Day, List<Task>> assignments) {
        this.assignments = assignments;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public int getTotalProfit() {
        return assignments.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(Task::getPoints)
                .sum();
    }


    @Override
    public String toString() {
        int totalProfit = getTotalProfit();
        return "Schedule{" +
                "id='" + id + '\'' +
                ", assignments=" + assignments +
                ", childId='" + childId + '\'' +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
