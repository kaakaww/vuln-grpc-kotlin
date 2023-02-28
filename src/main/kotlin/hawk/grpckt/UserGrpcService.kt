package hawk.grpckt

import com.google.protobuf.Empty
import hawk.UserServiceGrpcKt
import hawk.Users
import hawk.model.Search
import hawk.model.User
import hawk.service.UserSearchService
import hawk.service.UserService
import org.lognet.springboot.grpc.GRpcService
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@GRpcService
class UserGrpcService(private val userService: UserService, private val userSearchService: UserSearchService) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    var users: ConcurrentHashMap<Long, Users.User> = ConcurrentHashMap()
    private val counter = AtomicLong()

    override suspend fun addUser(request: Users.AddUserRequest): Users.AddUserResponse {
        val user = userService.addUser(
            User(
                name = request.user.name,
                description = request.user.description
            )
        )
        return Users.AddUserResponse.newBuilder().setUser(
            user.id?.let {
                Users.User.newBuilder()
                    .setDescription(user.description)
                    .setId(it)
                    .setName(user.name)
                    .build()
            }
        ).build()
    }

    override suspend fun getUser(request: Users.GetUserRequest): Users.GetUserResponse {
        val user = userService.getUser(request.id)
        return Users.GetUserResponse.newBuilder().setUser(
            user.id?.let {
                Users.User.newBuilder()
                    .setDescription(user.description)
                    .setId(it)
                    .setName(user.name)
                    .build()
            }
        ).build()
    }

    override suspend fun deleteUser(request: Users.DeleteUserRequest): Empty {
        userService.deleteUser(request.id)
        return super.deleteUser(request)
    }

    override suspend fun getUsers(request: Empty): Users.GetUsersResponse {
        users = ConcurrentHashMap()
        userService.getUsers().map {
            users[counter.incrementAndGet()] = it.id?.let { id ->
                Users.User.newBuilder()
                    .setDescription(it.description)
                    .setId(id)
                    .setName(it.name)
                    .build()
            }!!
        }
        return Users.GetUsersResponse.newBuilder().addAllUser(users.values).build()
    }
    override suspend fun getUsersByName(request: Users.GetUserByNameRequest): Users.GetUsersResponse {
        users = ConcurrentHashMap()
        userService.getUserByName(request.name).map {
            users[counter.incrementAndGet()] = it.id?.let { id ->
                Users.User.newBuilder()
                    .setDescription(it.description)
                    .setId(id)
                    .setName(it.name)
                    .build()
            }!!
        }
        return Users.GetUsersResponse.newBuilder().addAllUser(users.values).build()
    }

    override suspend fun getUsersByNameIsLike(request: Users.GetUserByNameRequest): Users.GetUsersResponse {
        users = ConcurrentHashMap()
        userService.getAllUsersByNameIsLike(request.name).map {
            users[counter.incrementAndGet()] = it.id?.let { id ->
                Users.User.newBuilder()
                    .setDescription(it.description)
                    .setId(id)
                    .setName(it.name)
                    .build()
            }!!
        }
        return Users.GetUsersResponse.newBuilder().addAllUser(users.values).build()
    }

    override suspend fun getUserBySearchText(request: Users.GetUserBySearchTextRequest): Users.GetUsersResponse {
        users = ConcurrentHashMap()
        userSearchService.search(
            Search(
                request.text
            )
        )?.map { it ->
            if (it != null) {
                users[counter.incrementAndGet()] = it.id?.let { id ->
                    Users.User.newBuilder()
                        .setDescription(it.description)
                        .setId(id)
                        .setName(it.name)
                        .build()
                }!!
            }
        }
        return Users.GetUsersResponse.newBuilder().addAllUser(users.values).build()
    }
}
