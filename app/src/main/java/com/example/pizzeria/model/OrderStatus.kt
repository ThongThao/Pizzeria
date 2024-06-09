@kotlinx.serialization.Serializable
enum class OrderStatus(code: String)  {
    Pending("Pending"),
    Shipping("Shipping"),
    Shipped("Shipped"),
    Canceled("Canceled"),
    Delivered("Delivered"),
}