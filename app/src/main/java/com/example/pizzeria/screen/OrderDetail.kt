package com.example.pizzeria.screen

import OrderStatus
import OrderViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.model.Order
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.green
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.menu
import com.example.pizzeria.ui.theme.yellow1
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailItemScreen(navController: NavController ,orderId: String,
                          orderViewModel: OrderViewModel = viewModel(),){
    val scrollState = rememberLazyListState()
    var orderDetail by remember(orderId) {
        mutableStateOf<Order?>(null)
    }


    LaunchedEffect(orderId) {
        orderViewModel.getOrderDetail(orderId).collect { order ->
            orderDetail = order
        }
    }
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.Cart.rout)
                },
                backgroundColor = yellow1,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(5.dp, 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ShoppingCart,
                    contentDescription = "")
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
                title = {
                    Text(
                        text = "Order Detail",
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
                            onClick = { },
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
                            Icon(
                                painterResource(id = R.drawable.ic_arrow_back),
                                null
                            )
                        }
                    }
                },
            )
        },
        bottomBar = {
            val selectedItem = remember { mutableStateOf("Ordered") }

            BottomAppBar(
                cutoutShape = CircleShape,
                backgroundColor = yellow1,
                contentColor = Color.White,
                elevation = 5.dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(topEnd = 13.dp, topStart = 13.dp)),
                content = {
                    BottomNavigation(
                        backgroundColor = yellow1,
                    ){
                        BottomNavigationItem(
                            selected =  selectedItem.value == "Home",
                            onClick = {
                                selectedItem.value = "Home"
                                navController.navigate(Screen.Home.rout)
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Home,
                                    contentDescription = "Home")
                            },
                            label = { androidx.compose.material.Text(text = "Home") },
                            alwaysShowLabel = false
                        )
                        BottomNavigationItem(
                            selected =  selectedItem.value == "Wishlist",
                            onClick = {
                                selectedItem.value = "Wishlist"
                                navController.navigate(Screen.Wishlist.rout)
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Favorite,
                                    contentDescription = "Wishlist")
                            },
                            label = { androidx.compose.material.Text(text = "Wishlist") },
                            alwaysShowLabel = false
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        BottomNavigationItem(
                            selected =  selectedItem.value == "Ordered",
                            onClick = {
                                selectedItem.value = "Ordered"
                                navController.navigate(Screen.Ordered.rout)
                            },
                            icon = {
                                Icon(
                                    painterResource(id = R.drawable.pending),
                                    contentDescription = "Ordered")
                            },
                            label = { androidx.compose.material.Text(text = "Order") },
                            alwaysShowLabel = false
                        )
                        BottomNavigationItem(
                            selected =  selectedItem.value == "Profile",
                            onClick = {
                                selectedItem.value = "Profile"
                                navController.navigate(Screen.Profile.rout)
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = "Profile")
                            },
                            label = { androidx.compose.material.Text(text = "Profile") },
                            alwaysShowLabel = false
                        )
                    }

                }
            )
        },

        ){

        LazyColumn(contentPadding = PaddingValues(bottom = 75.dp, start = 16.dp, end = 16.dp), state = scrollState){
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.DateRange,
                            contentDescription = null,
                            modifier = Modifier.height(20.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        orderDetail?.date?. let { date ->
                            val formattedDate = SimpleDateFormat(
                                "dd/MM/yyyy HH:mm",
                                Locale.getDefault()
                            ).format(date)
                            Text(
                                text = formattedDate,
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp,
                                color = blackcart
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = lightGray,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .padding(bottom = 2.dp),
                        shadowElevation = 6.dp,
                        onClick = {
                            //den trang sua/ them dia chi
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(15.dp)
                                .padding(top = 15.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 15.dp)
                            ) {
                                Text(
                                    text = "Name contact :",
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${orderDetail?.custumerName}",
                                    fontSize = 17.sp,
                                    color = blackcart,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 15.dp)
                            ) {
                                Text(
                                    text = "Phone number :",
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${orderDetail?.custumerPhone}",
                                    fontSize = 17.sp,
                                    color = blackcart,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 15.dp)
                            ) {
                                Text(
                                    text = "Address :",
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${orderDetail?.custumerAdd}",
                                    fontSize = 17.sp,
                                    color = blackcart,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 20.sp
                                )
                            }
                            orderDetail?.items.let {
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 15.dp)
                                ) {
                                    Text(
                                        text = "Items :",
                                        fontSize = 16.sp,
                                        color = Color.DarkGray
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        it?.forEach {
                                            Text(
                                                text = "${it.name} x${it.quantity}",
                                                fontSize = 17.sp,
                                                color = blackcart,
                                                fontWeight = FontWeight.Medium,
                                                lineHeight = 25.sp
                                            )
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(bottom = 15.dp)
                            ) {
                                Text(
                                    text = "Total :",
                                    fontSize = 16.sp,
                                    color = Color.DarkGray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "$${orderDetail?.total}",
                                    fontSize = 17.sp,
                                    color = blackcart,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            if (orderDetail?.status == "Pending") {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.img),
                                        contentDescription ="",
                                        tint = green,
                                        modifier = Modifier.size(40.dp))
                                    DashedLine(color = Color.Gray)
                                    Icon(
                                        painter = painterResource(id = R.drawable.img_1),
                                        contentDescription ="",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(40.dp))
                                    DashedLine(color = Color.Gray)
                                    Icon(
                                        painter = painterResource(id = R.drawable.img_2),
                                        contentDescription ="",
                                        tint=  Color.Gray,
                                        modifier = Modifier.size(40.dp))
                                }

                            }
                            if (orderDetail?.status == "Shipping") {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.img),
                                        contentDescription ="",
                                        tint = green,
                                        modifier = Modifier.size(40.dp))
                                    DashedLine(color = green)
                                    Icon(
                                        painter = painterResource(id = R.drawable.img_1),
                                        contentDescription ="",
                                        tint = green,
                                        modifier = Modifier.size(40.dp))
                                    DashedLine(color = Color.Gray)
                                    Icon(
                                        painter = painterResource(id = R.drawable.img_2),
                                        contentDescription ="",
                                        tint=  Color.Gray,
                                        modifier = Modifier.size(40.dp))
                                }

                            }
                            if (orderDetail?.status == "Shipped") {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.img),
                                        contentDescription ="",
                                        tint = green,
                                        modifier = Modifier.size(40.dp))
                                    DashedLine(color = green)
                                    Icon(
                                        painter = painterResource(id = R.drawable.img_1),
                                        contentDescription ="",
                                        tint = green,
                                        modifier = Modifier.size(40.dp))
                                    DashedLine(color = green)
                                    Icon(
                                        painter = painterResource(id = R.drawable.img_2),
                                        contentDescription ="",
                                        tint= green,
                                        modifier = Modifier.size(40.dp))
                                }

                            }
                            if (orderDetail?.status == "Shipped") {
                                Button(
                                    onClick = {
                                        updateOrderStatus(orderDetail?.id ?: "", OrderStatus.Delivered)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 15.dp,
                                            end = 15.dp,
                                            top = 25.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow1),
                                ) {
                                    Text(
                                        text = "Delivered",
                                        fontSize = 24.sp,
                                        color = Color.White
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
fun updateOrderStatus(orderId: String, newStatus: OrderStatus) {
    val db = FirebaseFirestore.getInstance()
    val orderRef = db.collection("orders").document(orderId)

    orderRef.update("status", newStatus.name)
        .addOnSuccessListener {
            Log.d("UpdateStatus", "Order status updated successfully.")
        }
        .addOnFailureListener { e ->
            Log.w("UpdateStatus", "Error updating order status", e)
        }
}
@Composable
fun DashedLine(color: Color) {
    Canvas(modifier = Modifier
        .height(10.dp)
        .width(65.dp)) {
        val dashWidth = 10f
        val dashGap = 5f
        var x = 0f
        while (x < size.width) {
            drawLine(
                color = color,
                start = Offset(x, 0f),
                end = Offset(x + dashWidth, 0f),
                strokeWidth = 4f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
            )
            x += dashWidth + dashGap
        }
    }
}
