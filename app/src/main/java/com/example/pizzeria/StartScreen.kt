package com.example.pizzeria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.ui.theme.yellow1

@Composable
fun StartScreen(navController: NavHostController){
    val offset = Offset(5.0f, 6.0f)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = yellow1),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(30.dp))
        Row {
            Text(
                text = "PIZZERIA",
                style = TextStyle(
                    fontSize = 38.sp,
                    color = red,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 9.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = offset,
                        blurRadius = 0.5f
                    )
                )
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Row {
            Text(
                text = "Delivering \n" +
                        "Deliciousness right\n" +
                        "to your door!",
                fontSize = 30.sp,
                letterSpacing = 1.sp,
                lineHeight = 35.sp,
                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        }
//        Spacer(modifier = Modifier.size(20.dp))
        Image(
            painter = painterResource(id = R.drawable._cham),
            contentDescription = "3doc",
            modifier = Modifier
                .size(60.dp)
        )

//        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = {
            navController.navigate(Screen.Home.rout)
        },
//            modifier = Modifier.height(45.dp),
            shape = RoundedCornerShape(40.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 15.dp,
                pressedElevation = 6.dp
            ),

            colors = ButtonDefaults.buttonColors(
                containerColor = red
            ),
            border = BorderStroke(0.5.dp, Color.Red)
        ) {
            Text(text = "START ORDER",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = "man",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
