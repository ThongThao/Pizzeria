package com.example.pizzeria.screen

import OrderViewModel
import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.bg
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.green
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.menu
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun OrderHistory(
    userId:String?,
    orderViewModel: OrderViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    navController: NavHostController
) {
    val scrollState = rememberLazyListState()
    val user by userViewModel.getUser(userId).observeAsState()
    val orders1 by orderViewModel.orders1.collectAsState()
    user?.let {
        orderViewModel.fetchOrdersForUser1(it.userID!!)
    }
    orders1?.let {order ->
        Scaffold(
            scaffoldState = rememberScaffoldState(),
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
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
                            text = "Order History",
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
                                onClick = {
                                          navController.popBackStack()
                                },
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

            ) {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 75.dp, start = 16.dp, end = 16.dp),
                state = scrollState
            ) {
                items(order) {
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
                            it.date?. let { date ->
                                val formattedDate = SimpleDateFormat(
                                    "dd/MM/yyyy HH:mm",
                                    Locale("vi", "VN")
                                ).format(date)
                                Text(
                                    text = formattedDate,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp,
                                    color = blackcart
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            androidx.compose.material.Surface(
                                onClick = {
                                    navController.navigate("order_detail/${user?.userID}/${it.id}")
                                },
                                shape = Shapes.small,
                                color = Color.White,
                                border = BorderStroke(1.dp, menu),
                                elevation = 1.dp
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .weight(1f)
                                    ) {
                                        androidx.compose.material.Text(
                                            text = "x${it.items?.size} items",
                                            color = Color.DarkGray,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier
                                                .padding(bottom = 6.dp)
                                        )
                                        androidx.compose.material.Text(
                                            text = "$${it.total}",
                                            color = Color.DarkGray,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier
                                                .padding(bottom = 6.dp)
                                        )
                                        androidx.compose.material.Text(
                                            text = "${it.status}",
                                            color = green,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        it.date?. let { date ->
                                            val formattedDate = SimpleDateFormat(
                                                "HH:mm",
                                                Locale.getDefault()
                                            ).format(date)
                                            androidx.compose.material.Text(
                                                text = formattedDate,
                                                color = grayFont,
                                                fontSize = 18.sp,
                                            )
                                        }
                                    }
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight(),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(imageVector = Icons.Rounded.KeyboardArrowRight,
                                            contentDescription = "",
                                            tint = grayFont,
                                            modifier = Modifier
                                                .padding(end = 5.dp)
                                                .clickable { })
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}
