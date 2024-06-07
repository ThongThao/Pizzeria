package com.example.pizzeria.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import com.example.pizzeria.R
import com.example.pizzeria.ui.theme.menu1
import com.example.pizzeria.ui.theme.yellow1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewBar(){
    var searchbarTxt by remember{ mutableStateOf("") }
    val isSearchbarFoucused = remember{ mutableStateOf(false) }

    val empty by remember{ mutableStateOf("") }

    var hintText by remember{ mutableStateOf("") }
    LaunchedEffect(key1 = true, key2 = isSearchbarFoucused.value ){

        val hintCategories = listOf("Pizza","Burger","Chicken fried","Drink","Fries")
        CoroutineScope(Dispatchers.IO).launch {
            var currentHintIndex = 0
            while (!isSearchbarFoucused.value){
                hintText= hintCategories[currentHintIndex]
                if(currentHintIndex >= hintCategories.size -1) currentHintIndex = 0 else currentHintIndex++
                Log.d("Hint_CHANGE","Looping ...")
                delay(1200L)
            }

        }

    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(43.dp),
            color = Color(0xF2FDF6DC),
            modifier = Modifier
                .fillMaxWidth()
//                .height(51.dp)
                .background(
                    color = yellow1,
                    shape = RoundedCornerShape(42.dp)
                )
                .padding(bottom = 1.dp)
            ,
            shadowElevation = 4.dp,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().height(49.dp),
                value = searchbarTxt,
                onValueChange = {searchbarTxt = it},

                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                placeholder = {
                    Text("Search $hintText", color = Color.Gray)
                },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .size(49.dp)
                            .background(color = yellow1, shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.Black,
                            modifier = Modifier
//                                .size(30.dp)
                        )
                    }
                },

            )
        }

    }
}

@Preview
@Composable
fun previewSearchViewBar(){
    SearchViewBar()
}