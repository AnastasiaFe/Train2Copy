package ua.nure.fedorenko.kidstim.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "child")
public class Child extends User implements Serializable {


    @Column(name = "gender")
    private int gender;

    @Column(name = "points")
    private int points;

    @Column(name = "birth")
    private long birth;

    public long getBirth() {
        return birth;
    }

    public void setBirth(long birth) {
        this.birth = birth;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child)) return false;
        if (!super.equals(o)) return false;

        Child child = (Child) o;

        if (getGender() != child.getGender()) return false;
        return getPoints() == child.getPoints();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getGender();
        result = 31 * result + getPoints();
        return result;
    }
}
