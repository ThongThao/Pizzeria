import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.Order
import com.example.pizzeria.model.OrderItem
import com.example.pizzeria.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID

class CartViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()

    fun addToCart(userId: String, cartItem: CartItem) {
        viewModelScope.launch {
            try {
                val userCartRef = firestore.collection("carts").document(userId)
                val cartSnapshot = userCartRef.get().await()
                val cart = if (cartSnapshot.exists()) {
                    cartSnapshot.toObject(Cart::class.java)
                } else {
                    null
                }
                if (cart != null) {
                    val updatedList = cart.listItem.toMutableList()
                    val existingItemIndex = updatedList.indexOfFirst { it.id == cartItem.id }
                    if (existingItemIndex != -1) {
                        val existingItem = updatedList[existingItemIndex]
                        val updatedItem = existingItem.copy(
                            quantity =cartItem.quantity,
                            price = cartItem.price
                        )
                        updatedList[existingItemIndex] = updatedItem
                    } else {
                        updatedList.add(cartItem)
                    }
                    userCartRef.update("listItem", updatedList)
                } else {
                    val newCart = Cart(
                        idCart = UUID.randomUUID().toString(),
                        idCustomer = userId,
                        listItem = listOf(cartItem),
                        total = cartItem.price * cartItem.quantity
                    )
                    userCartRef.set(newCart)
                }
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }


    fun saveCartToFirestore(userId: String, cart: Cart, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                firestore.collection("carts")
                    .document(userId)
                    .set(cart)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { e -> onError(e.message ?: "Error saving cart") }
            } catch (e: Exception) {
                onError(e.message ?: "Error saving cart")
            }
        }
    }
    private val _carts = MutableStateFlow<List<CartItem>>(emptyList())
    val carts:  StateFlow<List<CartItem>> = _carts

    fun getCart(userId: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("carts").document(userId).get().await()
                val cart = snapshot.toObject(Cart::class.java)
                _carts.value = (cart?.listItem ?: emptyList()) as List<CartItem>
            } catch (e: Exception) {
                // Handle exception
            }
        }
    }
    private val _carts2 = MutableStateFlow<List<Cart>>(emptyList())
    val carts2:  StateFlow<List<Cart>> = _carts2

    fun getCart2(userId: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("carts").document(userId).get().await()
                val cart = snapshot.toObject(Cart::class.java)
                if (cart != null) {
                    _carts2.value = listOf(cart)
                } else {
                    _carts2.value = emptyList()
                }
            } catch (e: Exception) {
                // Xử lý ngoại lệ
            }
        }
    }
    fun placeOrder(userId: String, user: User?, cartItems: List<CartItem>, note: String,total:Double) {
        viewModelScope.launch {
            try {
                // Tạo đối tượng Order từ thông tin hiện tại của giỏ hàng và thông tin của người dùng
                val order = Order(
                    id = UUID.randomUUID().toString(),
                    custumerid = userId,
                    custumerName = user?.fullName,
                    custumerPhone = user?.phoneNumber,
                    custumerAdd = user?.address,
                    date = Date(),
                    total = total,
                    note = note,
                    status = "Pending",
                    items = cartItems.map { cartItem ->
                        OrderItem(
                            id = cartItem.id,
                            name = cartItem.name,
                            description = cartItem.description,
                            image = cartItem.image,
                            price = cartItem.price,
                            quantity = cartItem.quantity
                        )
                    }
                )

                // Lưu đối tượng Order vào Firestore
                firestore.collection("orders").document(order.id!!)
                    .set(order)
                    .await()

                // Xóa giỏ hàng sau khi đặt hàng thành công (Tuỳ thuộc vào yêu cầu của ứng dụng)
                clearCart(userId)
            } catch (e: Exception) {

            }
        }
    }

    // Hàm xóa giỏ hàng sau khi đặt hàng thành công (Tuỳ thuộc vào yêu cầu của ứng dụng)
    private suspend fun clearCart(userId: String) {
        try {
            firestore.collection("carts").document(userId).delete().await()
        } catch (e: Exception) {
            // Xử lý exception
        }
    }

}