package hawk.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users", schema = "public")
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

    var name: String? = null
    var description: String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
}
