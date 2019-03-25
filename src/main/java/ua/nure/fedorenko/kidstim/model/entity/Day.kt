package ua.nure.fedorenko.kidstim.model.entity

import com.fasterxml.jackson.annotation.JsonInclude
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.ArrayList
import javax.persistence.*

@Entity
@Table(name = "days")
data class Day(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid")
        @Column(name = "id")
        var id: String,

        @Column
        var date: LocalDate? = null,
        @Column(name = "max_capacity")
        var maxCapacity: Int = 0

) {
    var realCapacity: Int = 0
    @JsonInclude
    @Transient
    var assignedTasks: List<Task> = ArrayList();

}