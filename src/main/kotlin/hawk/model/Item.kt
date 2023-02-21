package hawk.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class Item() {
    constructor(name: String, description: String) : this() {
        this.name = name
        this.description = description
    }

    constructor(id: Long, name: String?, description: String?) : this() {
        this.id = id
        this.name = name
        this.description = description
    }

    @Column(nullable = false)
    var name: String? = null
    var description: String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}
