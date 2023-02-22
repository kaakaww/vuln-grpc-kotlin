package hawk.exceptions

class ItemNotFoundException : RuntimeException {
    constructor() : super()
    constructor(id: String?) : super("item with $id not found")
    constructor(id: String?, cause: Throwable) : super("item with $id not found", cause)
}
