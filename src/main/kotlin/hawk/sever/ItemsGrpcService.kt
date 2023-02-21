package hawk.sever

import com.google.protobuf.Empty
import hawk.ItemServiceGrpcKt
import hawk.Items
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class ItemsGrpcService() : ItemServiceGrpcKt.ItemServiceCoroutineImplBase() {
    override suspend fun addItem(request: Items.AddItemRequest): Items.AddItemResponse {
        return super.addItem(request)
    }

    override suspend fun deleteItem(request: Items.DeleteItemRequest): Empty {
        return super.deleteItem(request)
    }

    override suspend fun getItem(request: Items.GetItemRequest): Items.GetItemResponse {
        return super.getItem(request)
    }

    override suspend fun getItems(request: Empty): Items.GetItemsResponse {
        return super.getItems(request)
    }
}
