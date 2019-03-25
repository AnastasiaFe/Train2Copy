package ua.nure.fedorenko.kidstim.model.entity

import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "child")
data class Child(
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

    @Column
    var password: String? = null

    @Column
    var photo: String? = null

    @Column(name = "device_token")
    var deviceToken: String? = null
    @Column
    var gender: Int = 0

    @Column
    var points: Int = 0

    @Column
    var birth: Long = 0
}