package hawk

import hawk.model.Item
import hawk.model.User
import hawk.repo.ItemRepo
import hawk.repo.UserRepo
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter
import java.util.Arrays
import java.util.stream.Stream

@SpringBootApplication
open class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Application>(*args)
        }

        @Value("\${spring.datasource.url}")
        private val dbUrl: String? = null

        @Bean
        fun commandLineRunner(ctx: ApplicationContext, itemRepo: ItemRepo, userRepo: UserRepo): CommandLineRunner {
            return CommandLineRunner {
                println("Let's inspect the beans provided by Spring Boot:")
                val beanNames = ctx.beanDefinitionNames
                Arrays.sort(beanNames)
                for (beanName in beanNames) {
                    println(beanName)
                }
                println(String.format("Load some fixture data %s", dbUrl))
                println(String.format("Items in DB %d", itemRepo.count()))
                if (itemRepo.count() == 0L) {
                    itemRepo.findAll().forEach { item: Item -> println(String.format("item: %s", item.name)) }
                    Stream.of(1, 2, 3)
                        .forEach { i: Int? ->
                            println(String.format("Adding item%d", i))
                            itemRepo.save(
                                Item(
                                    String.format("item%d", i),
                                    String.format("we have the best items, item%d", i)
                                )
                            )
                        }
                    println(String.format("Items in DB %d", itemRepo.count()))
                    itemRepo.findAll().forEach { item: Item -> println(String.format("item: %s", item.name)) }
                }
                println(String.format("Users in DB %d", userRepo.count()))
                if (userRepo.count() == 0L) {
                    userRepo.findAll().forEach { user: User -> println(String.format("user: %s", user.name)) }
                    Stream.of(1, 2, 3)
                        .forEach { i: Int? ->
                            println(String.format("Adding user%d", i))
                            userRepo.save(
                                User(
                                    String.format("user%d", i),
                                    String.format("we have the best users, users%d", i)
                                )
                            )
                        }
                    userRepo.save(User("user", "The auth user"))
                    Stream.of(4, 5, 6)
                        .forEach { i: Int? ->
                            println(String.format("Adding user%d", i))
                            userRepo.save(
                                User(
                                    String.format("user%d", i),
                                    String.format("we have the best users, user%d", i)
                                )
                            )
                        }
                    println(java.lang.String.format("Users in DB %d", userRepo.count()))
                    userRepo.findAll().forEach { user: User -> println(String.format("user: %s", user.name)) }
                }
            }
        }
    }
}

@Configuration
open class AppConfig {

    @Bean
    open fun protobufHttpMessageConverter(): ProtobufHttpMessageConverter {
        return ProtobufHttpMessageConverter()
    }
}
