package hawk.test

import com.google.protobuf.Empty
import com.google.protobuf.util.JsonFormat
import hawk.ItemServiceGrpc
import hawk.Items
import io.grpc.Grpc
import io.grpc.HttpConnectProxiedSocketAddress
import io.grpc.InsecureChannelCredentials
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.net.InetSocketAddress

class GrpcProxyClientTest {

    /**
     * Enable this to test proxying gRPC requests through a proxy
     *
     */
    @Disabled
    @Test
    fun testGrpcClientProxy() {
        val channel = Grpc
            .newChannelBuilder("localhost:9001", InsecureChannelCredentials.create())
            .proxyDetector { targetServerAddress ->
                HttpConnectProxiedSocketAddress.newBuilder()
                    .setProxyAddress(InetSocketAddress("localhost", 20000))
                    .setTargetAddress(targetServerAddress as InetSocketAddress)
                    .build()
            }
            .build()
        val itemSvcClient = ItemServiceGrpc.newBlockingStub(channel)
        val resp = itemSvcClient.addItem(
            Items.AddItemRequest.newBuilder()
                .setItemDetails(
                    Items.ItemDetails.newBuilder()
                        .setName("item uno")
                        .setType(Items.ItemType.MEDIUM)
                        .setDescription("all the best desc")
                )
                .build()
        )
        println(JsonFormat.printer().print(resp))

        val resp2 = itemSvcClient.getItems(Empty.getDefaultInstance())
        println(JsonFormat.printer().print(resp2))
    }
}
