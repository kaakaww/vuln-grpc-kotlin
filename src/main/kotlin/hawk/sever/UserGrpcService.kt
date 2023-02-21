package hawk.sever

import com.google.protobuf.Empty
import hawk.UserServiceGrpcKt
import hawk.Users
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class UserGrpcService : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    override suspend fun addUser(request: Users.AddUserRequest): Users.AddUserResponse {
        return super.addUser(request)
    }

    override suspend fun getUser(request: Users.GetUserRequest): Users.GetUserResponse {
        return super.getUser(request)
    }

    override suspend fun getUsers(request: Empty): Users.GetUsersResponse {
        return super.getUsers(request)
    }

    override suspend fun deleteUser(request: Users.DeleteUserRequest): Empty {
        return super.deleteUser(request)
    }
}
