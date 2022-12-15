package hawk.test

import com.google.protobuf.util.JsonFormat
import hawk.Items.AddItemRequest
import hawk.Items.ItemDetails
import hawk.Items.ItemType.MEDIUM
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

class ProtobufTests {

    @Disabled
    @Test
    fun testAdHoc() {
        val addItemReq = AddItemRequest.newBuilder()
            .apply {
                itemDetails = ItemDetails.newBuilder()
                    .apply {
                        type = MEDIUM
                        name = "the best item 1"
                        description = "The best description 1"
                    }.build()
            }.build()
        File("add-item-req.pb").outputStream().use {
            it.write(addItemReq.toByteArray())
        }

        File("add-item-req.json").outputStream().use {
            it.write(JsonFormat.printer().print(addItemReq).toByteArray())
        }
    }
}
