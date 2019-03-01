package ua.nure.fedorenko.kidstim.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "days")
public class Day {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column
    private LocalDate date;
    @Column(name = "max_capacity")
    private int maxCapacity;
    private int realCapacity;
    private List<Task> assignedTasks;

    public Day(String id, LocalDate date, int maxCapacity) {
        this.id = id;
        this.date = date;
        this.maxCapacity = maxCapacity;
        assignedTasks = new ArrayList<>();
    }

    public int getRealCapacity() {
        return realCapacity;
    }

    public void setRealCapacity(int realCapacity) {
        this.realCapacity = realCapacity;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public String getDayOfWeek() {
        return date.getDayOfWeek().name();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Day)) return false;

        Day day = (Day) o;

        return getId().equals(day.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        String dayOfWeek = getDayOfWeek();
        return "Day{" +
                "date=" + date +
                ", maxCapacity=" + maxCapacity +
                ", weekDay=" + dayOfWeek +
                '}';
    }
}
