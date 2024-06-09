package com.example.pizzeria.nav

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import com.example.pizzeria.R

sealed class Screen(val rout: String){
    object StartScreen : Screen("startscreen")
    object SignInScreen : Screen("signinscreen")
    object SignUpScreen : Screen("signupscreen")
    object Home : Screen(
        rout = "home/{userId}",
    ){
        fun createRoute(userId: String) = "home/$userId"
    }
    object Abc : Screen("abc")
    object Wishlist : Screen("wishlist/{userId}")
    {
        fun createRoute(userId: String) = "wishlist/$userId"
    }
    object Cart : Screen("Shopping Cart/{userId}")
    {
        fun createRoute(userId: String) = "Shopping Cart/$userId"
    }
    object Ordered : Screen("Ordered/{userId}"){
        fun createRoute(userId: String) = "Ordered/$userId"
    }
    object OrderHistory : Screen("OrderHistory/{userId}"){
        fun createRoute(userId: String) = "OrderHistory/$userId"
    }
    object Profile : Screen("Profile/{userId}"){
        fun createRoute(userId: String) = "Profile/$userId"
    }
    object Product: Screen( rout = "Product/{userId}",
    ){
        fun createRoute(userId: String) = "Product/$userId"
    }
    object Checkout: Screen( rout = "Checkout/{userId}",
    ){
        fun createRoute(userId: String) = "Checkout/$userId"
    }
    object Checkoutok: Screen( rout = "Checkoutok}",
    )

}
//
//sealed class NavScreen(
//    var id: Int,
//    val title: String,
//    @DrawableRes val selectedIconId: Int,
//    @DrawableRes val unSelectedIconId: Int,
//){
//    object Home:NavScreen(id=0, title = "Home", selectedIconId = R.drawable.home_, unSelectedIconId = R.drawable.home_)
//    object Wishlist:NavScreen(id=1, title = "Wishlist", selectedIconId = R.drawable.heart_, unSelectedIconId = R.drawable.heart_)
//    object Ordered:NavScreen(id=2, title = "Ordered", selectedIconId = R.drawable.pending, unSelectedIconId = R.drawable.pending)
//    object Profile:NavScreen(id=3, title = "Profile", selectedIconId = R.drawable.user, unSelectedIconId = R.drawable.user)
//    object Cart:NavScreen(id=4, title = "Shopping Cart", selectedIconId = R.drawable.shopping_cart, unSelectedIconId = R.drawable.shopping_cart)
//}
