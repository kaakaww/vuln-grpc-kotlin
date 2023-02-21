package hawk.service

import hawk.model.User
import hawk.repo.UserRepo

class UserService(private val userRepo: UserRepo) {
    fun addUser(user: User): User? {
        try {
            return userRepo.save(user)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun deleteUser(id: Long) {
        try {
            return userRepo.deleteById(id)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    fun getUser(id: Long): User? {
        try {
            return userRepo.findById(id).get()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun getUsers(): MutableIterable<User>? {
        try {
            return userRepo.findAll()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun getUserByName(name: String): User? {
        try {
            return userRepo.findByName(name)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
    fun getAllUsersByNameIsLike(name: String): List<User?>? {
        try {
            return userRepo.findAllByNameIsLike(name)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }
}
