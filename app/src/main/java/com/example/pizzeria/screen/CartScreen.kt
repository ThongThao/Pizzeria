package com.example.pizzeria.screen

import CartViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.model.User
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.delete
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.ui.theme.yellow2
import com.example.pizzeria.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(userId: String,
               navController: NavHostController,
               userViewModel: UserViewModel = viewModel(),
               cartViewModel: CartViewModel = viewModel(),
               navToCheckout:(User) -> Unit,
) {
    val user by userViewModel.getUser(userId).observeAsState()
    val carts by cartViewModel.carts.collectAsState()
    val carts2 by cartViewModel.carts2.collectAsState()
    userId?.let {
        cartViewModel.getCart(userId)
        cartViewModel.getCart2(userId)
    }

    user.let { user ->
        carts2!!.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White,
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    ),
                    title = {
                        androidx.compose.material3.Text(
                            text = "My Cart",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        Column(
                            modifier = Modifier.padding(start = 15.dp)
                        ) {
                            Button(
                                onClick = {  navController.popBackStack()},
                                contentPadding = PaddingValues(),
                                shape = Shapes.small,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.White,
                                    contentColor = Color.Black
                                ),
                                //                    elevation = 5.dp,
                                modifier = Modifier
                                    .width(38.dp)
                                    .height(38.dp)
                            ) {
                                androidx.compose.material.Icon(
                                    painterResource(id = R.drawable.ic_arrow_back),
                                    null
                                )
                            }
                        }
                    },
                )


                val scrollState = rememberLazyListState()
                carts?.let {
                    LazyColumn(
                        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
                        state = scrollState,
                        modifier = Modifier.height(455.dp)
                    ) {
                        it.forEach {
                            item {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = White,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp)
                                        .background(
                                            color = lightGray,
                                            shape = RoundedCornerShape(16.dp),
                                        )
                                        .padding(bottom = 2.dp),
                                    shadowElevation = 7.dp,
                                    onClick = {
                                        //den trang chi tiet
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = Shapes.small,
                                            modifier = Modifier.size(
                                                width = 121.dp,
                                                height = 121.dp
                                            )
                                        ) {
                                            Image(
                                                painter = rememberAsyncImagePainter(it.image),
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(2f)
                                                .padding(horizontal = 10.dp, vertical = 0.dp),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = "${it.name}",
                                                fontSize = 18.sp,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))

                                            Text(
                                                text = "$${it.price}",
                                                fontSize = 14.sp,
                                                color = grayFont,
                                                fontWeight = FontWeight.Medium
                                            ) //don gia

                                            Spacer(modifier = Modifier.height(6.dp))
                                            it.price?.let { price ->
                                                it.quantity?.let { quantity ->
                                                    val totalPrice = price * quantity
                                                    Text(
                                                        text = String.format("$%.2f", totalPrice), // hiển thị tổng tiền của mặt hàng với 2 chữ số thập phân
                                                        fontSize = 17.sp,
                                                        color = Color.Black,
                                                        style = MaterialTheme.typography.titleMedium,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            }

                                        }
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(0.3f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            var value by remember { mutableStateOf(it.quantity) }

                                            Button(
                                                onClick = { value++
                                                    cartViewModel.updateCartItemQuantity(userId, it, value)

                                                          },
                                                contentPadding = PaddingValues(),
                                                shape = CircleShape,
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Color(
                                                        0xFFFFF1D8
                                                    )
                                                ),
                                                modifier = Modifier
                                                    .width(20.dp)
                                                    .height(20.dp)
                                            ) {
                                                Icon(
                                                    painterResource(id = R.drawable.ic_plus),
                                                    null,
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))
                                            Box(
                                                modifier = Modifier
                                                    .width(23.dp)
                                                    .height(23.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        color = yellow1,
                                                        shape = CircleShape
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "$value",
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = 15.sp,
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Button(
                                                onClick = { if (value > 1) {
                                                    value--
                                                    cartViewModel.updateCartItemQuantity(userId, it, value)
                                                }
                                                          },
                                                contentPadding = PaddingValues(),
                                                shape = CircleShape,
                                                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = Color(
                                                        0xFFFFF1D8
                                                    )
                                                ),
                                                modifier = Modifier
                                                    .width(20.dp)
                                                    .height(20.dp)
                                            ) {
                                                Icon(
                                                    painterResource(id = R.drawable.ic_minus),
                                                    null,
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                )
                                            }

                                        }
                                    }
                                    Row() {
                                        androidx.compose.material3.Surface(
                                            onClick = {
                                                cartViewModel.removeItemFromCart(userId, it.id)
                                            },
                                            shape = CircleShape,
                                            color = delete
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "",
                                                tint = Color(0xFFFFFFFF),
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .padding(2.dp),
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {
                            Text(
                                "Enter your Discount code",
                                color = grayFont
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = yellow2,
                            unfocusedBorderColor = yellow1
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(42.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        trailingIcon = {
                            Button(
                                onClick = { /*TODO*/ },
                                shape = RoundedCornerShape(42.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = yellow1,
                                    contentColor = White
                                ),
                                modifier = Modifier
                                    .height(42.dp)
                                    .padding(end = 4.dp)
                            ) {
                                Text(
                                    text = "Apply",
                                    fontSize = 17.sp,
                                    color = White,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    )
                }
                carts2.let {
                    it.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 15.dp)
                        ) {

                            Text(
                                text = String.format("Subtotal: $%.2f", it.total),
                                fontSize = 18.sp,
                                color = blackcart,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "Fee Delivery: $ " + "0",
                                fontSize = 18.sp,
                                color = blackcart,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        BottomAppBar(
                            contentPadding = PaddingValues(),
                            backgroundColor = White,
                            modifier = Modifier
                                .height(100.dp)
                                .clip(
                                    shape = RoundedCornerShape(
                                        topEnd = 30.dp,
                                        topStart = 30.dp
                                    )
                                )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = "Total:",
                                        fontSize = 17.sp,
                                        color = grayFont,
                                        fontWeight = FontWeight.Normal
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = String.format("$%.2f", it.total),
                                        fontSize = 20.sp,
                                        color = Black,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Button(
                                    onClick = {
                                        user?.let { it1 -> navToCheckout(it1) }
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = red,
                                    ),
                                    modifier = Modifier.height(50.dp),
                                ) {
                                    Text(
                                        text = "Checkout ",
                                        fontSize = 19.sp,
                                        color = White,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_arrowrightx2),
                                        contentDescription = "",
                                        modifier = Modifier.size(25.dp)
                                    )
                                }
                            }
                        }
                    }
                }

            }

        }
    }
}
