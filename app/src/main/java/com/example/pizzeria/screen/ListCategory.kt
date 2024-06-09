package com.example.pizzeria.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.model.User
import com.example.pizzeria.ui.theme.black
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.ui.theme.yellow2
import com.example.pizzeria.viewmodel.CategoryViewModel
import com.example.pizzeria.viewmodel.ProductViewModel
import com.example.pizzeria.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListCategory(
    userId: String?,
    categoryName: String,
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    productViewModel: ProductViewModel = viewModel(),
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val productList by productViewModel.product1.collectAsState()
    val user by userViewModel.getUser(userId).observeAsState()

    // Fetch the products by category only once when the category is available
    categoryName?.let {
        productViewModel.fetchProductByCategory(it)
    }

    // Ensure Scaffold is not inside a loop and properly handle state
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
                title = {
                    Text(
                        "$categoryName",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        // Render the list of products
        user?.let {
            LayoutListItem1(productList, navController, user!!)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutListItem1(productList: List<ProductData>, navController: NavController,user: User) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 67.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(productList) { item ->
                Surface(
                    onClick = { navController.navigate("productDetail/${user.userID}/${item.productID}") },
                    shape = RoundedCornerShape(17.dp),
                    color = Color.White,
                    modifier = Modifier
                        .width(165.dp)
//                .height(210.dp)
                        .padding(vertical = 2.dp, horizontal = 2.dp)
                        .background(
                            color = yellow1,
                            shape = RoundedCornerShape(17.dp)
                        )
                        .padding(bottom = 1.dp),
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 14.dp, start = 10.dp, end = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(130.dp)
//                        .padding(5.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(item.productImage),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
//                        modifier = Modifier.clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "${item.productName}",
                            fontWeight = FontWeight.Medium,
                            color = black,
                            fontSize = 16.sp,
                            style = TextStyle(
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "${item.productPrice.toString()}",
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterVertically),
                                fontSize = 17.sp
                            )

                            Button(
                                modifier = Modifier.size(30.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = yellow1),
                                shape = CircleShape,
                                contentPadding = PaddingValues(4.dp),
                                onClick = {
//                            onClickToCart.invoke(productItem)
                                }
                            )
                            {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = Icons.Default.AddCircle,
//                            tint = Color.White,
                                    contentDescription = ""
                                )
                            }
                        }
                    }

                }
            }
        })
}


