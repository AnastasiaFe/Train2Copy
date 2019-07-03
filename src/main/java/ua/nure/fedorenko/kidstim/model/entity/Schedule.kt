package ua.nure.fedorenko.kidstim.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.time.LocalDate
import javax.persistence.*
@Entity
@Table(name = "schedules")
data class Schedule(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid")
        @Column(name = "id")
        var id: String? = null)
    : Serializable {


    @ManyToOne
    @JoinColumn(name = "child_id")
    var child: Child? = null
    @Column
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    var finishDate: Long? = null

}