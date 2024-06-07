package com.example.pizzeria.screen

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.black
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.delete
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.menu
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.ui.theme.yellow2

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(navController: NavHostController){
    Scaffold(

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
                            Text(
                                text = "My Cart",
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
                                androidx.compose.material.Icon(
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
            BottomAppBar(
                contentPadding = PaddingValues(),
                backgroundColor = menu,
                modifier = Modifier
                    .height(100.dp)
                    .clip(shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Row (
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
                            text = "$",
                            fontSize = 20.sp,
                            color = Black,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "43.00",
                            fontSize = 25.sp,
                            color = Black,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {

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
        },
        content = {
            val scrollState = rememberLazyListState()
            LazyColumn(contentPadding = PaddingValues(top = 100.dp, bottom = 110.dp, start = 16.dp, end = 16.dp), state = scrollState){
                item {
                    CartItemCard()
                    CartItemCard()
                    CartItemCard()
                    DiscountCode()
                    SubTotal()
                }
            }
        }
    )
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItemCard() {
        androidx.compose.material3.Surface(
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
                    modifier = Modifier.size(width = 121.dp, height = 121.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gamayo),
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
                        text = "Special Royal Beef Burger",
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "$11.99",
                        fontSize = 14.sp,
                        color = grayFont,
                        fontWeight = FontWeight.Medium
                    ) //don gia

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "$11.99",
                        fontSize = 17.sp,
                        color = black,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    ) //so luong x don gia

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Quantity()

                }
            }
            Row() {
                androidx.compose.material3.Surface(
                    onClick = {
                        // xoa
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

@Composable
fun Quantity() {
    var value by remember { mutableStateOf(1) }

    QuantityButton(
        R.drawable.ic_plus,
    ){ value++ }
    Spacer(modifier = Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .width(23.dp)
            .height(23.dp)
            .clip(CircleShape)
            .background(color = yellow1, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$value",
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
    QuantityButton(
        R.drawable.ic_minus,
    ){ value-- }
}

@Composable
fun QuantityButton(
    @DrawableRes iconDrawable: Int,
    color: Color = grayFont,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFF1D8), contentColor = color),
        elevation = elevation,
        modifier = Modifier
            .width(20.dp)
            .height(20.dp)
    ) {
        Icon(
            painterResource(id = iconDrawable),
            null,
            modifier = Modifier
                .size(16.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscountCode() {
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
                    color = grayFont)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = yellow2,
                unfocusedBorderColor = yellow1),
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = yellow1, contentColor = White),
                    modifier = Modifier
                        .height(42.dp)
                        .padding(end = 4.dp)
                ) {
                    Text(text = "Apply",
                        fontSize = 17.sp,
                        color = White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )
    }
}

@Composable
fun SubTotal() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 15.dp)
    ) {
        Text(
            text = "Subtotal: $ "+"43.00",
            fontSize = 18.sp,
            color = blackcart,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Fee Delivery: $ "+"0",
            fontSize = 18.sp,
            color = blackcart,
            fontWeight = FontWeight.Medium
        )
    }
}


@Preview
@Composable
fun previewCartScreen(){
    CartScreen(rememberNavController())
}