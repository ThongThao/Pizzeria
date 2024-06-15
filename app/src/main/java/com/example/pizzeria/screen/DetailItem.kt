package com.example.pizzeria.screen

import Cart
import CartItem
import CartViewModel
import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.AppBarCollapsedHeight
import com.example.pizzeria.ui.theme.AppBarExpendedHeight
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.ui.theme.redlight
import com.example.pizzeria.viewmodel.FavoriteViewModel
import com.example.pizzeria.viewmodel.ProductDetailViewModel
import com.example.pizzeria.viewmodel.UserViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.max
import kotlin.math.min

@SuppressLint("SuspiciousIndentation, UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailItem(
    navController: NavHostController, productId: String?,
    userId: String?,
    userViewModel: UserViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
) {
    val favoriteViewModel: FavoriteViewModel = viewModel()
    val user by userViewModel.getUser(userId).observeAsState()
    if (productId == null) {
        Text("Error: Product ID is null")
        return
    }

    val viewModel: ProductDetailViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProductDetailViewModel(productId) as T
            }
        }
    )
    var value by remember { mutableStateOf(1) }
    val product by viewModel.product.collectAsState()
    val scrollState = rememberLazyListState()
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset =
        with(LocalDensity.current) { imageHeight.roundToPx() } - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset
    user.let {
Column(modifier = Modifier
    .fillMaxWidth()
    .fillMaxHeight()
    .padding(top = 10.dp)) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Button(
            onClick = {navController.popBackStack()},
            contentPadding = PaddingValues(),
            shape = Shapes.small,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = grayFont),
            elevation = ButtonDefaults.elevation(),
            modifier = Modifier
                .width(38.dp)
                .height(38.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_arrow_back),
                null
            )
        }
            product?.productID?.let { productId ->
                FavoriteButton(
                    userId = userId!!,
                    productId = productId,
                    favoriteViewModel = favoriteViewModel
                )
            }

    }
    Column {
        Box(
            Modifier
                .height(imageHeight)
                .graphicsLayer {
                    alpha = 1f - offsetProgress
                }) {
            Image(
                painter = rememberAsyncImagePainter(product?.productImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                Pair(0.4f, Color.Transparent),
                                Pair(1f, Color.White)
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    "${product?.productType}",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(Shapes.small)
                        .background(lightGray)
                        .padding(vertical = 6.dp, horizontal = 16.dp),
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .height(AppBarCollapsedHeight),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "${product?.productName}",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = (16 + 28 * offsetProgress).dp)
                    .scale(1f - 0.25f * offsetProgress)
            )
        }
    }
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_clock, "40 min")
        InfoColumn(R.drawable.ic_flame, "328 kcal")
        InfoColumn(R.drawable.ic_star, "4.9")
    }
    Text(
        text = "${product?.productDescription}",
//        fontWeight = Medium,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
        style = TextStyle(
            color = Color.DarkGray
        ),
        fontSize = 15.sp
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(Shapes.medium)
//            .border(border = BorderStroke(1.dp, Color(0xFFFFE9A6)), shape = Shapes.medium)
            .background(lightGray)
            .padding(horizontal = 20.dp)
    ) {

        Text(
            text = "$${product?.productPrice.toString()}",
            Modifier.weight(2f),
            fontWeight = FontWeight.Bold,
            fontSize = 21.sp
        )

        CircularButton(
            iconResouce = R.drawable.ic_minus,
            elevation = null,
            color = red
        ) {if (value > 1) {
            value-- }}

        Text(
            text = "$value",
            Modifier.padding(16.dp),
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )

        CircularButton(
            iconResouce = R.drawable.ic_plus,
            elevation = null,
            color = red
        ) { value++ }
    }
    FloatingActionButton(
        onClick = {
            val cartItem = CartItem(
                id = product!!.productID!!, // Assuming you have itemId in menuItem
                name = product!!.productName!!,
                description = product!!.productDescription!!,
                image = product!!.productImage!!,
                price = product!!.productPrice!!,
                quantity = value,
            )
            cartViewModel.addToCart(user?.userID!!,cartItem) // Function to generate unique cart id
            navController.navigate(Screen.Cart.createRoute(user?.userID!!))

        },
        shape = CircleShape,
        backgroundColor = redlight,
        contentColor = Color.White,
        modifier = Modifier.padding(start = 155.dp)
    ) {

        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "",
            modifier = Modifier.size(32.dp)
        )
    }
}

    }
}

@Composable
fun InfoColumn(@DrawableRes iconResouce: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResouce),
            contentDescription = null,
            tint = redlight,
            modifier = Modifier.height(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResouce: Int,
    color: Color = grayFont,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = Shapes.small,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = color),
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(
            painterResource(id = iconResouce),
            null
        )
    }
}
@Composable
fun FavoriteButton(
    userId: String,
    productId: String,
    favoriteViewModel: FavoriteViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val isFavorite = remember { mutableStateOf(false) }

    LaunchedEffect(userId, productId) {
        isFavorite.value = favoriteViewModel.isFavorite(userId, productId)
    }

    val icon: ImageVector = if (isFavorite.value) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    Button(
        onClick = {
            coroutineScope.launch {
                if (isFavorite.value) {
                    favoriteViewModel.removeFavorite(userId, productId)
                } else {
                    favoriteViewModel.addFavorite(userId, productId)
                }
                isFavorite.value = !isFavorite.value
            }
        },
        contentPadding = PaddingValues(),
        shape = Shapes.small,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = grayFont),
        elevation = ButtonDefaults.elevation(),
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = "Toggle Favorite",
            tint = Color.Red,
        )
    }
}

