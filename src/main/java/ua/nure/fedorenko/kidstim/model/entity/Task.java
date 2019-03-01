package ua.nure.fedorenko.kidstim.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Task implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @Column
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private TaskStatus status;

    @Column
    private int duration;
    @Column(name = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private LocalDateTime creationDate;

    @Column(name = "expiration_date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private LocalDate expirationDate;
    @Column
    private int points;
    @Column(name = "min_freq")
    private int minFrequency = 1;
    @Column(name = "max_freq")
    private int maxFrequency = 1;

    private int scheduledFrequency;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "child_task", joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "child_id", referencedColumnName = "id")})
    private List<Child> children;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    private Parent parent;

    public Task(String id, String desc, int points, int duration, LocalDate expirationDate) {
        this.id = id;
        this.description = desc;
        this.points = points;
        this.duration = duration;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        return getId().equals(task.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public int getDuration() {
        return duration;
    }

    public int getScheduledFrequency() {
        return scheduledFrequency;
    }

    public void setScheduledFrequency(int scheduledFrequency) {
        this.scheduledFrequency = scheduledFrequency;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public int getMinFrequency() {
        return minFrequency;
    }

    public void setMinFrequency(int minFrequency) {
        this.minFrequency = minFrequency;
    }

    public int getMaxFrequency() {
        return maxFrequency;
    }

    public void setMaxFrequency(int maxFrequency) {
        this.maxFrequency = maxFrequency;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", expirationDate=" + expirationDate +
                ", points=" + points +
                ", minFrequency=" + minFrequency +
                ", maxFrequency=" + maxFrequency +
                '}';
    }


}
