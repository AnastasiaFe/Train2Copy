package ua.nure.fedorenko.kidstim.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Day(LocalDate date, int maxCapacity) {
        this.date = date;
        this.maxCapacity = maxCapacity;
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
