package ua.nure.fedorenko.kidstim.model.entity

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "assignments")
data class Assignment(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid")
        @Column(name = "id")
        var id: String? = null,
        @OneToOne
        @JoinColumn(name = "day")
        var day: Day? = null
) : Serializable {

    @OneToMany
    @JoinColumn(name = "task_id")
    var tasks: List<Task>? = ArrayList()
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    var schedule: Schedule? = null

}