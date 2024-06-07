package com.example.pizzeria.nav

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.register.SignUpScreen
import com.example.pizzeria.StartScreen
import com.example.pizzeria.login.SignInScreen
import com.example.pizzeria.screen.CartScreen
import com.example.pizzeria.screen.CheckOutSuccess
import com.example.pizzeria.screen.DetailItem
import com.example.pizzeria.screen.HomeScreen
import com.example.pizzeria.screen.OrderedScreen
import com.example.pizzeria.screen.ProductListItem
import com.example.pizzeria.screen.ProfileScreen
import com.example.pizzeria.screen.WishlistScreen
import com.example.pizzeria.ui.theme.yellow1

@Composable
fun MyNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.StartScreen.rout
    ){
        composable(Screen.StartScreen.rout){
            StartScreen(navController = navController)
        }
        composable(Screen.Home.rout){
            HomeScreen(navController = navController)
        }
        composable(Screen.SignInScreen.rout){
            SignInScreen(navController = navController)
        }
        composable(Screen.SignUpScreen.rout){
            SignUpScreen(navController = navController)
        }
        composable(Screen.Wishlist.rout){
            WishlistScreen(navController = navController)
        }
        composable(Screen.Cart.rout){
            CartScreen(navController = navController)
        }
        composable(Screen.Ordered.rout){
            OrderedScreen(navController = navController)
        }
        composable(Screen.Profile.rout){
            ProfileScreen(navController = navController)
        }
        composable(Screen.Product.rout){
            ProductListItem(navController = navController)
        }

        composable("itemDetail/{productName}") { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName")
            DetailItem(productName)
        }
    }
}

@Composable
fun myFAB(navController: NavHostController){

    FloatingActionButton(
        onClick = {
            navController.navigate(Screen.Cart.rout)
        },
        backgroundColor = yellow1,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(5.dp, 6.dp)
    ) {
        androidx.compose.material.Icon(
            imageVector = Icons.Rounded.ShoppingCart,
            contentDescription = "")
    }
}

@Composable
fun BottomNav(navController: NavHostController){

    val selectedItem = remember { mutableStateOf("Home") }

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
//                        navController.navigate(Screen.Home.rout)
                    },
                    icon = {
                        androidx.compose.material.Icon(
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
                        androidx.compose.material.Icon(
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
//                        navController.navigate(Screen.Ordered.rout)
                    },
                    icon = {
                        androidx.compose.material.Icon(
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
//                        navController.navigate(Screen.Profile.rout)
                    },
                    icon = {
                        androidx.compose.material.Icon(
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

@Preview
@Composable
fun PreviewBottomNav(){
    BottomNavigation()
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BottomNavigation() {
    Scaffold(
        scaffoldState = rememberScaffoldState(),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            myFAB(rememberNavController())
        },
        bottomBar = {
            BottomNav(rememberNavController())
        }
    ) {

    }
}














