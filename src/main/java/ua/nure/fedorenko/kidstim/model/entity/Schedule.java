package ua.nure.fedorenko.kidstim.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    LocalDate finishDate;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*

        public int getTotalProfit() {
            return assignments.stream()
                    .flatMap(x -> x.getTasks().stream())
                    .mapToInt(Task::getPoints)
                    .sum();
        }

    */

}
