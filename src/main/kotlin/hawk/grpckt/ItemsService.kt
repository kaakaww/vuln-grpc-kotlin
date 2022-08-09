package hawk.grpckt

import hawk.ItemServiceGrpcKt
import hawk.Items.AddItemRequest
import hawk.Items.AddItemResponse
import hawk.Items.GetItemsRequest
import hawk.Items.GetItemsResponse
import hawk.Items.Item
import org.lognet.springboot.grpc.GRpcService
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@GRpcService
class ItemsService : ItemServiceGrpcKt.ItemServiceCoroutineImplBase() {

    val counter = AtomicLong()
    val items = ConcurrentHashMap<Long, Item>()

    override suspend fun addItem(request: AddItemRequest): AddItemResponse {
        val id = counter.incrementAndGet()
        val item = Item.newBuilder().setId(id).setDetails(request.itemDetails).build()
        items[id] = item
        return AddItemResponse.newBuilder().setItem(item).build()
    }

    override suspend fun getItems(request: GetItemsRequest): GetItemsResponse {
        return GetItemsResponse.newBuilder().addAllItems(items.values).build()
    }
}
