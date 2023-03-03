package hawk.grpckt

import com.google.protobuf.Empty
import hawk.ItemServiceGrpcKt
import hawk.Items
import hawk.model.Item
import hawk.model.Search
import hawk.service.ItemSearchService
import hawk.service.ItemService
import org.lognet.springboot.grpc.GRpcService
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@GRpcService
class ItemsGrpcService(private val itemService: ItemService, private val itemSearchService: ItemSearchService) :
    ItemServiceGrpcKt.ItemServiceCoroutineImplBase() {

    var items: ConcurrentHashMap<Long, Items.Item> = ConcurrentHashMap()
    private val counter = AtomicLong()

    override suspend fun addItem(request: Items.AddItemRequest): Items.AddItemResponse {
        val item = itemService.addItem(
            Item(
                name = request.itemDetails.name,
                description = request.itemDetails.description
            )
        )
        return Items.AddItemResponse.newBuilder().setItem(
            item.id?.let {
                Items.Item.newBuilder()
                    .setId(item.id!!)
                    .setDetails(
                        Items.ItemDetails.newBuilder()
                            .setName(item.name)
                            .setDescription(item.description)
                            .build()
                    )
                    .build()
            }
        ).build()
    }

    override suspend fun deleteItem(request: Items.DeleteItemRequest): Empty {
        itemService.deleteItem(request.id)
        return super.deleteItem(request)
    }

    override suspend fun getItem(request: Items.GetItemRequest): Items.GetItemResponse {
        val item = itemService.getItem(request.id)
        return Items.GetItemResponse.newBuilder().setItem(
            item.id?.let {
                Items.Item.newBuilder()
                    .setId(item.id!!)
                    .setDetails(
                        Items.ItemDetails.newBuilder()
                            .setName(item.name)
                            .setDescription(item.description)
                            .build()
                    )
                    .build()
            }
        ).build()
    }

    override suspend fun getItems(request: Empty): Items.GetItemsResponse {
        items = ConcurrentHashMap()
        itemService.getItems().map {
            items[counter.incrementAndGet()] = it.id.let { id ->
                Items.Item.newBuilder()
                    .setId(id!!)
                    .setDetails(
                        Items.ItemDetails.newBuilder()
                            .setName(it.name)
                            .setDescription(it.description)
                            .build()
                    )
                    .build()
            }
        }
        return Items.GetItemsResponse.newBuilder().addAllItems(items.values).build()
    }

    override suspend fun getByNameContainingOrDescriptionContaining(request: Items.GetItemByNameRequest): Items.GetItemsResponse {
        items = ConcurrentHashMap()
        itemService.getByNameContainingOrDescriptionContaining(request.name, request.description).map {
            items[counter.incrementAndGet()] = it.id.let { id ->
                Items.Item.newBuilder()
                    .setId(id!!)
                    .setDetails(
                        Items.ItemDetails.newBuilder()
                            .setName(it.name)
                            .setDescription(it.description)
                            .build()
                    )
                    .build()
            }
        }
        return Items.GetItemsResponse.newBuilder().addAllItems(items.values).build()
    }

    override suspend fun getByNameOrDescription(request: Items.GetItemByNameRequest): Items.GetItemsResponse {
        items = ConcurrentHashMap()
        itemService.getByNameOrDescription(request.name, request.description).map {
            items[counter.incrementAndGet()] = it.id.let { id ->
                Items.Item.newBuilder()
                    .setId(id!!)
                    .setDetails(
                        Items.ItemDetails.newBuilder()
                            .setName(it.name)
                            .setDescription(it.description)
                            .build()
                    )
                    .build()
            }
        }
        return Items.GetItemsResponse.newBuilder().addAllItems(items.values).build()
    }

    override suspend fun getItemBySearchText(request: Items.GetItemBySearchTextRequest): Items.GetItemsResponse {
        items = ConcurrentHashMap()
        itemSearchService.search(
            Search(
                request.text
            )
        )?.map { it ->
            if (it != null) {
                items[counter.incrementAndGet()] = it.id.let { id ->
                    Items.Item.newBuilder()
                        .setId(id!!)
                        .setDetails(
                            Items.ItemDetails.newBuilder()
                                .setName(it.name)
                                .setDescription(it.description)
                                .build()
                        )
                        .build()
                }
            }
        }
        return Items.GetItemsResponse.newBuilder().addAllItems(items.values).build()
    }
}
