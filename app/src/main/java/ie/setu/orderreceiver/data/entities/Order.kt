package ie.setu.orderreceiver.data.entities

data class Order(
    //java.lang.RuntimeException: Could not deserialize object. Class ie.setu.orderreceiver.data.entities.Order does not define a no-argument constructor.
    val itemId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val completed: Boolean = false,
    val timestamp: Long = 0L,
    val userId: String = "",
    val imageUri: String = ""
)
