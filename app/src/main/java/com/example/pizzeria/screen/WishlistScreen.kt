package com.example.pizzeria.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.nav.BottomNav
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.nav.myFAB
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.yellow1

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WishlistScreen(navController: NavHostController){
    val scrollState = rememberLazyListState()

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(horizontal = 15.dp, vertical = 15.dp)
                        .background(color = Color.White)
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
                                text = "My Wish List",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
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
                        },
                    )
                }
            }
        },
        bottomBar = {
            val selectedItem = remember { mutableStateOf("Wishlist") }

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
                            label = { Text(text = "Home") },
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
                            label = { Text(text = "Wishlist") },
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
                            label = { Text(text = "Order") },
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
                            label = { Text(text = "Profile") },
                            alwaysShowLabel = false
                        )
                    }

                }
            )
        }

    ) {

    }
}