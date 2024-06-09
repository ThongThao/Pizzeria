data class Cart(
    val idCart: String = "",
    val idCustomer: String = "",
    val listItem: List<CartItem> = listOf(),
    val total: Double = 0.0
)

data class CartItem(
    val id: String= "",
    val name: String= "",
    val description: String= "",
    val image: String= "",
    val price: Double=0.0,
    val quantity: Int=0,
)
