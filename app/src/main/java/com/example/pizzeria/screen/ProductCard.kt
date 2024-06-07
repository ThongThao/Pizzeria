package com.example.pizzeria.screen

import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.model.CategoryData
import com.example.pizzeria.model.ProductData
import com.example.pizzeria.ui.theme.black
import com.example.pizzeria.ui.theme.blackcart
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.ui.theme.yellow1
import com.example.pizzeria.ui.theme.yellow2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(productList: List<ProductData>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(5.dp)
    ) {
        items(productList) {item ->

            Surface(
                onClick = { /*TODO*/ },
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
                            color = Black,
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
    }
}
