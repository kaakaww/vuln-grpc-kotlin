package hawk.interceptor

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor
import org.jboss.logging.Logger

@GrpcGlobalServerInterceptor
class UnaryServerInterceptor : ServerInterceptor {

    companion object {
        val LOGGER: Logger = Logger.getLogger(this::class.java)
    }

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        call: ServerCall<ReqT, RespT>?,
        headers: Metadata?,
        next: ServerCallHandler<ReqT, RespT>
    ): ServerCall.Listener<ReqT> {
        LOGGER.info(call)
        return next.startCall(call, headers)
    }
}