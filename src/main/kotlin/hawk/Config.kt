package hawk

import hawk.repo.ItemRepo
import hawk.repo.UserRepo
import hawk.service.ItemSearchService
import hawk.service.ItemService
import hawk.service.UserSearchService
import hawk.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.persistence.EntityManager

@Configuration
@EnableJpaRepositories
open class Config : WebMvcConfigurer {

    @Autowired lateinit var itemRepo: ItemRepo

    @Autowired lateinit var userRepo: UserRepo

    @Autowired lateinit var entityManager: EntityManager

    @Bean
    open fun iTemSearchService(): ItemSearchService? {
        return ItemSearchService(entityManager)
    }

    @Bean
    open fun userSearchService(): UserSearchService? {
        return UserSearchService(entityManager)
    }

    @Bean
    open fun userService(): UserService? {
        return UserService(userRepo)
    }

    @Bean
    open fun itemService(): ItemService? {
        return ItemService(itemRepo)
    }
}
