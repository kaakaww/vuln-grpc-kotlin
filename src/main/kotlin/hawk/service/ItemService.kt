package hawk.service

import hawk.model.Item
import hawk.repo.ItemRepo

class ItemService(private val itemRepo: ItemRepo) {

    fun addItem(item: Item): Item? {
        try {
            return itemRepo.save(item)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun deleteItem(id: Long) {
        try {
            return itemRepo.deleteById(id)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    fun getItem(id: Long): Item? {
        try {
            return itemRepo.findById(id).get()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun getItems(): MutableIterable<Item>? {
        try {
            return itemRepo.findAll()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun getByNameOrDescription(name: String?, description: String?): List<Item?>? {
        try {
            return itemRepo.findByNameOrDescription(name, description)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun getByNameContainingOrDescriptionContaining(name: String?, description: String?): List<Item?>? {
        try {
            return itemRepo.findByNameContainingOrDescriptionContaining(name, description)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}
