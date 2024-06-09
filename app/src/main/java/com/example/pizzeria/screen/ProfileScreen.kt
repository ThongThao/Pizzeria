package com.example.pizzeria.screen

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.model.User
import com.example.pizzeria.nav.BottomNav
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.nav.myFAB
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.bg
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.delete
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.menu
import com.example.pizzeria.ui.theme.menu1
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.ui.theme.yellow2
import com.example.pizzeria.uploadToStorage
import com.example.pizzeria.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    userId: String?,
    navToOrder:(User) -> Unit,
    navController: NavHostController,
    context:Context,
    userViewModel: UserViewModel = viewModel(),

    ) {

    val user by userViewModel.getUser(userId).observeAsState()
    user?.let {
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        // Handle result of activity for result
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
                imageUri = uri
                // Upload image to Firestore and Storage
                imageUri?.let {
                    uploadToStorage(it, context, "profile_image") { imageUrl ->
                        user?.let {
                            val updatedUser = it.copy(
                                image = imageUrl.toString()
                            )
                            userViewModel.updateUser(userId, updatedUser,
                                onSuccess = {
                                    navController.navigate(Screen.Profile.createRoute(user!!.userID!!))
                                },
                                onFailure = { e ->
                                }
                            )
                        }                    }
                }
            }

        // Composable function to handle click on profile image
        fun onProfileImageClicked() {
            // Open file picker to choose an image
            launcher.launch("image/*")

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
                        contentDescription = ""
                    )
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
                            .padding(horizontal = 15.dp)
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
                                    text = "Profile",
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
                val selectedItem = remember { mutableStateOf("Profile") }

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
                        ) {
                            BottomNavigationItem(
                                selected = selectedItem.value == "Home",
                                onClick = {
                                    selectedItem.value = "Home"
                                    navController.navigate(Screen.Home.rout)
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Home,
                                        contentDescription = "Home"
                                    )
                                },
                                label = { Text(text = "Home") },
                                alwaysShowLabel = false
                            )
                            BottomNavigationItem(
                                selected = selectedItem.value == "Wishlist",
                                onClick = {
                                    selectedItem.value = "Wishlist"
                                    navController.navigate(Screen.Wishlist.rout)
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Favorite,
                                        contentDescription = "Wishlist"
                                    )
                                },
                                label = { Text(text = "Wishlist") },
                                alwaysShowLabel = false
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            BottomNavigationItem(
                                selected = selectedItem.value == "Ordered",
                                onClick = {
                                    selectedItem.value = "Ordered"
                                    navController.navigate(Screen.Ordered.rout)
                                },
                                icon = {
                                    Icon(
                                        painterResource(id = R.drawable.pending),
                                        contentDescription = "Ordered"
                                    )
                                },
                                label = { Text(text = "Order") },
                                alwaysShowLabel = false
                            )
                            BottomNavigationItem(
                                selected = selectedItem.value == "Profile",
                                onClick = {
                                    selectedItem.value = "Profile"
                                    navController.navigate(Screen.Profile.rout)
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.Rounded.Person,
                                        contentDescription = "Profile"
                                    )
                                },
                                label = { Text(text = "Profile") },
                                alwaysShowLabel = false
                            )
                        }

                    }
                )
            },

            content = {
                val scrollState = rememberLazyListState()

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 100.dp),
                    state = scrollState,
                    modifier = Modifier
                        .background(color = bg)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(color = bg),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ConstraintLayout() {
                                val (topImg, profile) = createRefs()
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .constrainAs(topImg) {
                                            top.linkTo(parent.top)
                                            start.linkTo(parent.start)
                                        }
                                        .background(
                                            color = menu,
                                            shape = RoundedCornerShape(
                                                bottomEnd = 150.dp,
                                                bottomStart = 150.dp
                                            )
                                        )
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(2.dp)
                                        .constrainAs(profile) {
                                            top.linkTo(topImg.bottom)
                                            bottom.linkTo(topImg.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier
                                            .padding(top = 30.dp)
                                    ) {
                                        ProfileImage(
                                            modifier = Modifier.padding(16.dp),
                                            image = user?.image ?: "", // Pass user's current image URL
                                            onClick = ::onProfileImageClicked // Handle click event
                                        )
                                        Text(
                                            text = "${user!!.fullName}",
                                            color = blackcart,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(top = 14.dp, bottom = 7.dp)
                                        )
                                        Text(
                                            text = "${user!!.email}",
                                            color = grayFont,
                                            fontSize = 18.sp
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 5.dp, start = 25.dp, end = 25.dp)
                                    .height(55.dp)
                            ) {
                                Surface(
                                    onClick = {
                                        navController.navigate("profile/${user!!.userID}")
                                    },
                                    shape = Shapes.small,
                                    color = Color.White,
                                    border = BorderStroke(1.dp, menu),
                                    elevation = 1.dp
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight(),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(imageVector = Icons.Rounded.Person,
                                                contentDescription = "",
                                                tint = grayFont,
                                                modifier = Modifier
                                                    .padding(start = 10.dp, end = 5.dp)
                                                    .clickable {
                                                        navController.navigate("profile/${user!!.userID}")
                                                    }
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .weight(1f)
                                        ) {
                                            Text(
                                                text = "Edit Profile",
                                                color = Color.DarkGray,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
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
                                                    .clickable { navController.navigate("profile/${user!!.userID}") })
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 5.dp, start = 25.dp, end = 25.dp)
                                    .height(55.dp)
                            ) {
                                Surface(
                                    onClick = {
                                              navToOrder(user!!)
                                    },
                                    shape = Shapes.small,
                                    color = Color.White,
                                    border = BorderStroke(1.dp, menu),
                                    elevation = 1.dp
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight(),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                painterResource(id = R.drawable.pending),
                                                contentDescription = "",
                                                tint = grayFont,
                                                modifier = Modifier
                                                    .padding(start = 10.dp, end = 5.dp)
                                                    .clickable { }
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .weight(1f)
                                        ) {
                                            Text(
                                                text = "Order History",
                                                color = Color.DarkGray,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 5.dp, start = 25.dp, end = 25.dp)
                                    .height(55.dp)
                            ) {
                                Surface(
                                    onClick = { /*TODO*/ },
                                    shape = Shapes.small,
                                    color = Color.White,
                                    border = BorderStroke(1.dp, menu),
                                    elevation = 1.dp
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight(),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(imageVector = Icons.Rounded.Notifications,
                                                contentDescription = "",
                                                tint = grayFont,
                                                modifier = Modifier
                                                    .padding(start = 10.dp, end = 5.dp)
                                                    .clickable { }
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .weight(1f)
                                        ) {
                                            Text(
                                                text = "Notification",
                                                color = Color.DarkGray,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
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
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 5.dp, start = 25.dp, end = 25.dp)
                                    .height(55.dp)
                            ) {
                                Surface(
                                    onClick = { /*TODO*/ },
                                    shape = Shapes.small,
                                    color = Color.White,
                                    border = BorderStroke(1.dp, menu),
                                    elevation = 1.dp
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight(),
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(imageVector = Icons.Rounded.Settings,
                                                contentDescription = "",
                                                tint = grayFont,
                                                modifier = Modifier
                                                    .padding(start = 10.dp, end = 5.dp)
                                                    .clickable { }
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(start = 16.dp)
                                                .weight(1f)
                                        ) {
                                            Text(
                                                text = "Settings",
                                                color = Color.DarkGray,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
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

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, bottom = 5.dp, start = 25.dp, end = 25.dp)
                            ) {
                                androidx.compose.material3.Button(
                                    onClick = {
                                        navController.navigate(Screen.StartScreen.rout)
                                    },
                                    modifier = Modifier
                                        .height(50.dp),
                                    shape = CircleShape,
                                    elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(
                                        defaultElevation = 5.dp,
                                        pressedElevation = 6.dp
                                    ),
                                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFD32C2C)
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.power),
                                        contentDescription = "",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(end = 5.dp)
                                    )

                                    androidx.compose.material3.Text(
                                        text = "LogOut",
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 17.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    image: String,
    onClick: () -> Unit // Add onClick parameter
) {
    val context = LocalContext.current
    val placeholderImage = painterResource(id = R.drawable.profile) // Default image

    val imagePainter = if (image.isNullOrEmpty()) {
        placeholderImage
    } else {
        rememberAsyncImagePainter(image)
    }

    Surface(
        onClick = onClick, // Handle click event
        color = Color.White,
        shape = CircleShape,
        modifier = modifier.size(120.dp),
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .border(
                    border = BorderStroke(3.dp, yellow1),
                    shape = CircleShape
                )
                .clickable { onClick() } // Also handle click event
        )
    }
}




