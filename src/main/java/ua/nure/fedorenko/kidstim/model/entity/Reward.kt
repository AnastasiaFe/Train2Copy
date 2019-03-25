package ua.nure.fedorenko.kidstim.model.entity

import java.io.Serializable
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table
data class Reward(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid")
        @Column(name = "id")
        var id: String? = null,

        @Column(name = "description")
        var description: String? = null,

        @Column(name = "points")
        var points: Int = 0,

        @Enumerated(EnumType.ORDINAL)
        @Column(name = "status")
        var status: RewardStatus? = null
) : Serializable {


    @ManyToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.MERGE), (CascadeType.REFRESH)])
    @JoinTable(name = "child_reward", joinColumns = [(JoinColumn(name = "reward_id"))], inverseJoinColumns = [(JoinColumn(name = "child_id"))])
    var children: List<Child>? = ArrayList()

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent")
    var parent: Parent? = null
}