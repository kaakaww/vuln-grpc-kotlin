package hawk.repo

import hawk.model.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepo : CrudRepository<Item, Long> {
    fun findByNameOrDescription(name: String?, description: String?): List<Item>
    fun findByNameContainingOrDescriptionContaining(name: String?, description: String?): List<Item>
}
