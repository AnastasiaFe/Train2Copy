package ua.nure.fedorenko.kidstim.model.entity

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "parent")
data class Parent(
        @Id
        @GeneratedValue(generator = "uuid")
        @GenericGenerator(name = "uuid", strategy = "uuid")
        @Column(name = "id")
        var id: String,
        @Column
        var email: String,
        @Column
        var name: String? = null,
        @Column
        var surname: String? = null
) : Serializable {

    @ManyToMany(fetch = FetchType.LAZY, cascade = [(CascadeType.ALL)])
    @JoinTable(name = "parent_child", joinColumns = [(JoinColumn(name = "parent_id"))], inverseJoinColumns = [(JoinColumn(name = "child_id"))])
    var children: List<Child>? = ArrayList()
    @Column
    var password: String? = null

    @Column
    var photo: String? = null

    @Column(name = "device_token")
    var deviceToken: String? = null
}