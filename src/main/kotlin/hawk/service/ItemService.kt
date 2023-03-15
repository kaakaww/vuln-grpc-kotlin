package hawk.service

import hawk.model.Item
import hawk.repo.ItemRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class ItemService(private val itemRepo: ItemRepo) {

    suspend fun addItem(item: Item): Item = withContext(IO) {
        itemRepo.save(item)
    }

    suspend fun deleteItem(id: Int) = withContext(IO) {
        itemRepo.deleteById(id)
    }

    suspend fun getItem(id: Int): Item = withContext(IO) {
        itemRepo.findById(id).get()
    }

    suspend fun getItems(): MutableIterable<Item> = withContext(IO) {
        itemRepo.findAll()
    }

    suspend fun getByNameContainingOrDescriptionContaining(name: String?, description: String?): List<Item> = withContext(IO) {
        itemRepo.findByNameContainingOrDescriptionContaining(name, description)
    }
}
