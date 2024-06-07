package com.example.pizzeria.nav

import androidx.annotation.DrawableRes
import com.example.pizzeria.R

sealed class Screen(val rout: String){
    object StartScreen : Screen("startscreen")
    object SignInScreen : Screen("signinscreen")
    object SignUpScreen : Screen("signupscreen")
    object Home : Screen("home")
    object Abc : Screen("abc")
    object Wishlist : Screen("wishlist")
    object Cart : Screen("Shopping Cart")
    object Ordered : Screen("Ordered")
    object Profile : Screen("Profile")
    object Product: Screen("Product")
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
