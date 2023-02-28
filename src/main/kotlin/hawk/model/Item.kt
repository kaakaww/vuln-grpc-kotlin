package hawk.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "item", schema = "public")
open class Item() {
    constructor(name: String, description: String) : this() {
        name.also { this.name = it }
        description.also { this.description = it }
    }

    constructor(id: Long, name: String?, description: String?) : this() {
        id.also { this.id = it }
        name.also { this.name = it }
        description.also { this.description = it }
    }

    open var name: String? = null
    open var description: String? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null
}
