package hawk.exceptions

class UserNotFoundException : RuntimeException {
    constructor() : super()
    constructor(id: String?) : super("user with $id not found")
    constructor(id: String?, cause: Throwable) : super("user with $id not found", cause)
}
