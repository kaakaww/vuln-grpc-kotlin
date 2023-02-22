package hawk.service

import hawk.model.User
import hawk.repo.UserRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserService(private val userRepo: UserRepo) {
    suspend fun addUser(user: User): User = withContext(IO) {
        userRepo.save(user)
    }
    suspend fun deleteUser(id: Long) = withContext(IO) {
        userRepo.deleteById(id)
    }
    suspend fun getUser(id: Long): User = withContext(IO) {
        userRepo.findById(id).get()
    }
    suspend fun getUsers(): MutableIterable<User> = withContext(IO) {
        userRepo.findAll()
    }
    suspend fun getUserByName(name: String): List<User> = withContext(IO) {
        userRepo.findByName(name)
    }
    suspend fun getAllUsersByNameIsLike(name: String): List<User> = withContext(IO) {
        userRepo.findAllByNameIsLike(name)
    }
}
