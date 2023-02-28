package hawk.grpckt

import hawk.exceptions.ItemNotFoundException
import hawk.exceptions.UserNotFoundException
import io.grpc.Status
import io.grpc.StatusException
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.web.bind.MethodArgumentNotValidException

@GrpcAdvice
class GrpcExceptionAdvice {

    @GrpcExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): StatusException {
        val status = Status.INTERNAL.withDescription(ex.message).withCause(ex)
        return status.asException().also { log.error("status: $status") }
    }

    @GrpcExceptionHandler(ItemNotFoundException::class)
    fun handleItemNotFoundException(ex: ItemNotFoundException): StatusException {
        val status = Status.INVALID_ARGUMENT.withDescription(ex.message).withCause(ex)
        return status.asException().also { log.error("status: $status") }
    }

    @GrpcExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException): StatusException {
        val status = Status.INVALID_ARGUMENT.withDescription(ex.message).withCause(ex)
        return status.asException().also { log.error("status: $status") }
    }

    @GrpcExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): StatusException {
        val errorMap: MutableMap<String, String> = HashMap()
        ex.bindingResult.fieldErrors.forEach { error -> error.defaultMessage?.let { errorMap[error.field] = it } }
        val status = Status.INVALID_ARGUMENT.withDescription(errorMap.toString()).withCause(ex)
        return status.asException().also { log.error("status: $status") }
    }

    @GrpcExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): StatusException {
        val status = Status.INVALID_ARGUMENT.withDescription(ex.toString()).withCause(ex)
        return status.asException().also { log.error("status: $status") }
    }

    companion object {
        private val log = LoggerFactory.getLogger(GrpcExceptionAdvice::class.java)
    }
}
