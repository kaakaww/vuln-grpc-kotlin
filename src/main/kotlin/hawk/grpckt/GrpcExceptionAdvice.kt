package hawk.grpckt

import io.grpc.Status
import io.grpc.StatusException
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.slf4j.LoggerFactory

@GrpcAdvice
class GrpcExceptionAdvice {


    @GrpcExceptionHandler(Exception::class)
    fun handleException(ex: Exception): StatusException {
        val status = Status.INVALID_ARGUMENT.withDescription(ex.stackTraceToString()).withCause(ex)
        return status.asException().also { log.error("status: $status") }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GrpcExceptionAdvice::class.java)
    }
}
