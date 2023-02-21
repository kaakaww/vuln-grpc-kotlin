package hawk.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
open class User() {

    constructor(name: String, description: String) : this() {
        this.name = name
        this.description = description
    }

    constructor(id: Long, name: String, description: String) : this() {
        this.id = id
        this.name = name
        this.description = description
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    lateinit var name: String
    lateinit var description: String
}
