package com.example.pizzeria.screen

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.User
import com.example.pizzeria.nav.BottomNav
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.nav.myFAB
import com.example.pizzeria.ui.theme.green
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.viewmodel.CategoryViewModel
import com.example.pizzeria.viewmodel.ProductViewModel
import com.example.pizzeria.viewmodel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.text.Collator
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter, UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(userId:String?,
               navController: NavHostController,
               navToAll:(User) -> Unit,
               navToCart:(User) -> Unit,
               navToOrder:(User) -> Unit,
               navToWishList:(User) -> Unit,
               userViewModel: UserViewModel = viewModel(),
               categoryViewModel: CategoryViewModel = viewModel(),
               productViewModel: ProductViewModel = viewModel(),
               ) {
    val user by userViewModel.getUser(userId).observeAsState()
    val categoryList by categoryViewModel.categories.collectAsState()
    val productList by productViewModel.products.collectAsState()
    user?.let {
        Scaffold(
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navToCart(it)
                    },
                    backgroundColor = yellow1,
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(5.dp, 6.dp)
                ) {
                    androidx.compose.material.Icon(
                        imageVector = Icons.Rounded.ShoppingCart,
                        contentDescription = ""
                    )
                }
            },
            topBar = {
                HeaderLocation(user!!)
            },
            bottomBar = {
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
                        ) {
                            BottomNavigationItem(
                                selected = selectedItem.value == "Home",
                                onClick = {
                                    selectedItem.value = "Home"
//                        navController.navigate(Screen.Home.rout)
                                },
                                icon = {
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Rounded.Home,
                                        contentDescription = "Home"
                                    )
                                },
                                label = { androidx.compose.material.Text(text = "Home") },
                                alwaysShowLabel = false
                            )
                            BottomNavigationItem(
                                selected = selectedItem.value == "Wishlist",
                                onClick = {
                                    selectedItem.value = "Wishlist"
                                    navToWishList(it)
                                },
                                icon = {
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Rounded.Favorite,
                                        contentDescription = "Wishlist"
                                    )
                                },
                                label = { androidx.compose.material.Text(text = "Wishlist") },
                                alwaysShowLabel = false
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            BottomNavigationItem(
                                selected = selectedItem.value == "Ordered",
                                onClick = {
                                    selectedItem.value = "Ordered"
                                    navToOrder(it)
//                        navController.navigate(Screen.Ordered.rout)
                                },
                                icon = {
                                    androidx.compose.material.Icon(
                                        painterResource(id = R.drawable.pending),
                                        contentDescription = "Ordered"
                                    )
                                },
                                label = { androidx.compose.material.Text(text = "Order") },
                                alwaysShowLabel = false
                            )
                            BottomNavigationItem(
                                selected = selectedItem.value == "Profile",
                                onClick = {
                                    selectedItem.value = "Profile"
                                    navController.navigate(Screen.Profile.createRoute(user!!.userID!!))

                                },
                                icon = {
                                    androidx.compose.material.Icon(
                                        imageVector = Icons.Rounded.Person,
                                        contentDescription = "Profile"
                                    )
                                },
                                label = { androidx.compose.material.Text(text = "Profile") },
                                alwaysShowLabel = false
                            )
                        }

                    }
                )
            },
            content = {

                val scrollState = rememberLazyListState()
                LazyColumn(contentPadding = PaddingValues(bottom = 110.dp), state = scrollState) {
                    item() {

                        SearchViewBar()

                        Spacer(modifier = Modifier.height(10.dp))

                        ListCategory(user!!,categoryList,navController)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Popular Items",
                                    fontWeight = FontWeight.W300,
                                    fontSize = 15.sp,
                                    letterSpacing = 1.sp,
                                    color = Color.Black
                                )

                                TextButton(
                                    onClick = {
                                        user?.let {
                                            navToAll(it)
                                        }
                                    },
                                ) {
                                    Text(
                                        text = "All",
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    )
                                }
                            }

                            ProductCard(productList, navController, user!!)
                        }

                        SliderBanner()
                    }
                }
            }
        )

    }
}
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ListCategory(user:User,categoryList: List<CategoryData>,navController:NavHostController) {
        val collator = Collator.getInstance(Locale("vi", "VN"))
        val sortedCategoryList = categoryList.sortedWith(compareBy(collator) { it.categoryName })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(sortedCategoryList) { item ->
                    Surface(
                        onClick = {
                            navController.navigate("productList/${user.userID}/${item.categoryName}")
                            Log.d("Navigation", "UserID: ${user.userID}, Category: ${item.categoryName}")

                        },
                        shape = RoundedCornerShape(42.dp),
                        color = yellow1,
                        modifier = Modifier
                            .width(120.dp)
                            .height(47.dp)
                            .padding(end = 10.dp, start = 3.dp)
                            .background(
                                MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(42.dp)
                            )
                            .padding(bottom = 1.dp),
                        shadowElevation = 6.dp,
                    ) {
                        Row(
                            modifier = Modifier.padding(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Surface(
                                shape = CircleShape,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .size(39.dp),
                                color = Color(0xFFF5F0E4)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(item.categoryImage),
                                    contentDescription = "",
                                    modifier = Modifier.padding(3.dp)
                                    //                        contentScale = ContentScale.Crop,
                                )
                            }
                            Text(
                                text = "${item.categoryName}",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderLocation(user: User){

    //location
    var location by remember { mutableStateOf("") }
    getLocation(LocalContext.current) { specificLocation ->
        // Cập nhật biến location với chuỗi địa chỉ cụ thể
        location = "$specificLocation"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .height(70.dp)
                .padding(horizontal = 15.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(240.dp)
                        .background(Color.White)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.location),
                            contentDescription = "location icon",
                            modifier = Modifier
                                .size(15.dp)
                                .align(Alignment.CenterStart),
                            tint = Color.Unspecified
                        )

                        Text(
                            text = location,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .widthIn(max = 210.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_down),
                            contentDescription = "location icon",
                            modifier = Modifier
                                .size(15.dp)
                                .align(Alignment.CenterEnd),
                            tint = Color.Unspecified
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.list),
                        contentDescription = "List",
                        modifier = Modifier.height(40.dp),
                        tint = Color.Unspecified
                    )
                },
                actions = {
                    ProfileImage1(image = user.image.toString())
                }
            )
        }
    }
}
@Composable
fun ProfileImage1(modifier: Modifier = Modifier, image: String) {

        val placeholderImage = painterResource(id = R.drawable.avar)// Thay thế bằng ID của ảnh mặc định của bạn

        val imagePainter = if (image.isNullOrEmpty()) {
            placeholderImage
        } else {
            rememberAsyncImagePainter(model = image)
        }
        Image(
            painter = imagePainter,
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(42.dp)
                .padding(3.dp)
                .clip(RoundedCornerShape(15.dp))
        )

}

//ham lay vi tri
@SuppressLint("MissingPermission")
fun getLocation(context: Context, onLocationReceived: (String) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000 // 10 seconds
        fastestInterval = 5000 // 5 seconds
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                // Sử dụng Geocoder để chuyển đổi kinh độ và vĩ độ thành địa chỉ cụ thể
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses?.firstOrNull()

                // Tạo chuỗi địa chỉ cụ thể từ thông tin trả về từ Geocoder
                val specificLocation = address?.let {
                    "${it.featureName ?: ""}, ${it.thoroughfare ?: ""}, ${it.subLocality ?: ""}, ${it.locality ?: ""}, ${it.subAdminArea ?: ""}, ${it.adminArea ?: ""}, ${it.countryName ?: ""}"
                } ?: "Unknown Location"

                // Gửi chuỗi địa chỉ cụ thể đến hàm callback
                onLocationReceived(specificLocation)
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

