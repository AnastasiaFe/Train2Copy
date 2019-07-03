package ua.nure.fedorenko.kidstim.model.entity

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.*

@Entity
@Table
data class Task(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid")
        @Column(name = "id")
        var id: String? = null,

        @Column
        var description: String? = null,

        @Enumerated(EnumType.ORDINAL)
        @Column
        var status: TaskStatus? = null,

        @Column
        var duration: Int = 0,

        @Column(name = "creation_date")
       // @field:JsonDeserialize(using = LocalDateTimeFromEpochDeserializer::class)
        var creationDate: Long? = null,

        @Column(name = "expiration_date")
        var expirationDate: Long? = null,
        @Column
        var points: Int = 0,
        @Column(name = "min_freq")
        var minFrequency: Int = 1,
        @Column(name = "max_freq")
        var maxFrequency: Int = 0
) : Serializable {


    @Transient
    var scheduledFrequency: Int = 0
    @ManyToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.MERGE), (CascadeType.REFRESH)])
    @JoinTable(name = "child_task", joinColumns = [(JoinColumn(name = "task_id", referencedColumnName = "id"))], inverseJoinColumns = [(JoinColumn(name = "child_id", referencedColumnName = "id"))])
    var children: List<Child>? = ArrayList()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    var parent: Parent? = null

    constructor(id: String, desc: String, points: Int, duration: Int, expirationDate: Long) : this() {
        this.id = id
        this.description = desc
        this.points = points
        this.duration = duration
        this.expirationDate = expirationDate
    }

}