import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    val listItemInCart = cart.listItem?.toMutableList() ?: mutableListOf()
                    Log.d("addToCart",listItemInCart.toString())
                    var existingItem: CartItem? = null

                    for (item in listItemInCart) {
                        if (item.id == cartItem.id) {
                            existingItem = item
                            Log.d("addToCart","Da co trong gio hang")
                            item.quantity = cartItem.quantity
                            break
                        }
                    }

                    if (existingItem == null) {
                        Log.d("addToCart","Chua co trong gio hang")
                        listItemInCart.add(cartItem)
                    }
                    val updatedTotal = listItemInCart.sumByDouble { it.price * it.quantity }
                    userCartRef.update("total", updatedTotal)
                    userCartRef.update("listItem", listItemInCart)
                } else {
                    val newCart = Cart(
                        idCart = UUID.randomUUID().toString(),
                        idCustomer = userId,
                        listItem = listOf(cartItem), // Start with the new cartItem
                        total = cartItem.price * cartItem.quantity
                    )
                    userCartRef.set(newCart)
                }
            } catch (e: Exception) {
                // Handle exception
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
                Log.d("firebasecart",cart.toString())
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
    fun updateCartItemQuantity(userId: String, cartItem: CartItem, newQuantity: Int) {
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
                    val updatedList = cart.listItem!!.toMutableList()
                    val existingItemIndex = updatedList.indexOfFirst { it.id == cartItem.id }
                    if (existingItemIndex != -1) {
                        val existingItem = updatedList[existingItemIndex]
                        val updatedItem = existingItem.copy(
                            quantity = newQuantity,
                            price = existingItem.price
                        )
                        updatedList[existingItemIndex] = updatedItem
                    }
                    userCartRef.update("listItem", updatedList)
                    // Optionally update the total price
                    val updatedTotal = updatedList.sumByDouble { it.price * it.quantity }
                    userCartRef.update("total", updatedTotal)
                }
            } catch (e: Exception) {
                // Handle exception
            }
            getCart(userId)
            getCart2(userId)
        }
    }
    fun removeItemFromCart(userId: String, itemId: String) {
        viewModelScope.launch {
            try {
                val userCartRef = firestore.collection("carts").document(userId)
                val cartSnapshot = userCartRef.get().await()
                val cart = cartSnapshot.toObject(Cart::class.java)
                if (cart != null) {
                    val updatedList = cart.listItem?.toMutableList() ?: mutableListOf()
                    val itemToRemove = updatedList.find { it.id == itemId }
                    if (itemToRemove != null) {
                        updatedList.remove(itemToRemove)
                        userCartRef.update("listItem", updatedList)
                        // Optionally update the total price
                        val updatedTotal = updatedList.sumByDouble { it.price * it.quantity }
                        userCartRef.update("total", updatedTotal)
                    }
                }
            } catch (e: Exception) {
                // Handle exception
            }
            getCart(userId)
            getCart2(userId)
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