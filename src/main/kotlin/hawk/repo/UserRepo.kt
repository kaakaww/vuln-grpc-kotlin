package hawk.repo

import hawk.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepo : CrudRepository<User, Long> {

    fun findByName(name: String?): User?
    fun findAllByNameIsLike(name: String?): List<User?>?
}
