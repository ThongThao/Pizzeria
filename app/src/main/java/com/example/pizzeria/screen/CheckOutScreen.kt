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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzeria.R
import com.example.pizzeria.ui.theme.Shapes
import com.example.pizzeria.ui.theme.black
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.delete
import com.example.pizzeria.ui.theme.green
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.red

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CheckOutScreen(){
    val scrollState = rememberLazyListState()
    Scaffold(
        topBar = {
            CheckOutHeader()
        },
        bottomBar = {
            CheckOutFooter()
        },
    ) { paddingValues ->
        CheckOutContent(scrollState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutHeader() {
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
                        text = "Check Out",
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
}

@Composable
fun CheckOutContent(scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = 90.dp, bottom = 160.dp, start = 16.dp, end = 16.dp), state = scrollState){
        item {
            CheckItem()
            ShippingInfo()
            PaymentMethod()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethod() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 5.dp)
    ) {
        InforHeader(R.drawable.credit_card, "Payment Method")

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
            Row(
                modifier = Modifier.padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cash on delivery",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "",
                    tint = green,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingInfo() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 5.dp)
    ) {
        InforHeader(R.drawable.shiplocation, "Shipping Information")

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
            Row(
                modifier = Modifier.padding(10.dp),
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f)
                        .padding(horizontal = 5.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    //ten nguoi dat hang
                    Text(
                        text = "Hoang Quynh",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //so dien thoai
                    Text(
                        text = "0986351772",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    //so duong
                    Text(
                        text = "365 Tran Dai Nghia",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // quan/ huyen/ thanh pho
                    Text(
                        text = "Hoa Hai, Ngu Hanh Son, Da Nang, Viet Nam",
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.3f)
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InfoActive()
                }
            }
        }
    }
}

@Composable
fun InfoActive() {
    Icon(
        imageVector = Icons.Rounded.CheckCircle,
        contentDescription = "",
        tint = green,
        modifier = Modifier.size(20.dp)
    )
    Spacer(modifier = Modifier.height(30.dp))

    InfoActiveButton(
        R.drawable.pencil,
        background = blackcart
    )
    Spacer(modifier = Modifier.height(10.dp))
    InfoActiveButton(
        R.drawable.delete,
        background = delete
    )
}

@Composable
fun InfoActiveButton(
    @DrawableRes iconDrawable: Int,
    color: Color = White,
    background: Color = White,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = background, contentColor = color),
        elevation = elevation,
        modifier = Modifier
            .width(25.dp)
            .height(25.dp)
    ) {
        androidx.compose.material3.Icon(
            painterResource(id = iconDrawable),
            null,
            modifier = Modifier
                .size(16.dp),
            tint = White
        )
    }
}


@Composable
fun InforHeader(@DrawableRes iconResouce: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResouce),
            contentDescription = null,
            modifier = Modifier.height(20.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = blackcart
            )
    }
}

@Composable
fun CheckItem() {
    Column {
        Text(
            text = "Items",
            fontWeight = FontWeight.Medium,
            color = black,
            fontSize = 16.sp
        )
        ListCardItemCO()
    }
}

@Composable
fun ListCardItemCO() {
    LazyRow(
//        horizontalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(3.dp)
    ){
        items(5){
            CardItemCO()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardItemCO() {
    Surface(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(17.dp),
        color = Color.White,
        modifier = Modifier
            .width(165.dp)
            .padding(vertical = 6.dp, horizontal = 6.dp)
            .background(
                color = lightGray,
                shape = RoundedCornerShape(17.dp)
            )
            .padding(bottom = 2.dp),
        shadowElevation = 5.dp
    ){
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
                    painter = painterResource(id = R.drawable.pizza_smoked_salmon_74190_5108),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
//                        modifier = Modifier.clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Pizza Mozzarella Size L",
                fontWeight = FontWeight.Medium,
                color = black,
                fontSize = 16.sp,
                style = TextStyle(
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
fun CheckOutFooter() {
    BottomAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(160.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Total:",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "$",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "43.00",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = red,
                ),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Pay Now",
                    fontSize = 19.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Preview()
@Composable
fun previewCheckOutScreen() {
    CheckOutScreen()
}
