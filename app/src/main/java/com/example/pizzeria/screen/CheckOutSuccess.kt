package com.example.pizzeria.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.nav.BottomNav
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.nav.myFAB
import com.example.pizzeria.ui.theme.grayFont
import com.example.pizzeria.ui.theme.green
import com.example.pizzeria.ui.theme.lightGray
import com.example.pizzeria.ui.theme.menu
import com.example.pizzeria.ui.theme.red


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutSuccess(
    navController: NavHostController,
    userId:String
){
    Scaffold (
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = menu)
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = RoundedCornerShape(25.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shadowElevation = 10.dp,
                    onClick = {
                    }
                ){
                    Column(
                        modifier = Modifier.padding(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "",
                            tint = green,
                            modifier = Modifier.size(110.dp)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Success!",
                            fontSize = 35.sp,
                            color = green,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Your menu items for today are estimated " +
                                    "to delivery within 25-45 minutes.",
                            fontSize = 16.sp,
                            color = grayFont,
                            style = TextStyle(
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.width(200.dp),
                            lineHeight = 21.sp
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        Button(
                            onClick = {
                               navController.navigate(Screen.Ordered.createRoute(userId))
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = green,
                            ),
                            modifier = Modifier
                                .height(70.dp)
                                .width(230.dp),
                        ) {
                            Text(
                                text = "DONE",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    )
}

