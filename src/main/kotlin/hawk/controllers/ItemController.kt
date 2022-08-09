package hawk.controllers

import hawk.ItemServiceGrpc.ItemServiceBlockingStub
import hawk.Items.GetItemsRequest
import hawk.Items.GetItemsResponse
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/items")
class ItemController {

    @GrpcClient("grpcItemService")
    lateinit var grpcItemService: ItemServiceBlockingStub

    @RequestMapping(
        "list",
        produces = ["application/x-protobuf"],
        method = [RequestMethod.GET]
    )
    fun getItems(): ResponseEntity<GetItemsResponse> {
        val grpcRequest = GetItemsRequest.newBuilder().build()
        val ret = grpcItemService.getItems(grpcRequest)
        return ResponseEntity.ok(ret)
    }
}
