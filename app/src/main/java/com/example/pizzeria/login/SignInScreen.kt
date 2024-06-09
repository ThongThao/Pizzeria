package com.example.pizzeria.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.model.User
import com.example.pizzeria.nav.Screen
import com.example.pizzeria.ui.theme.PizzeriaTheme
import com.example.pizzeria.ui.theme.bg
import com.example.pizzeria.ui.theme.red
import com.example.pizzeria.viewmodel.SignInViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavHostController,
    signInViewModel: SignInViewModel = viewModel(),
 navToHome:(User) -> Unit,
){
    val viewModel: SignInViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val user by viewModel.user.observeAsState()
        Box(modifier = Modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.bg_login),
                contentDescription = "",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds)
        }
        Spacer(modifier = Modifier.size(30.dp))
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Text(text = "Welcome Back ", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(text = " PIZZERIA!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = red,)
            }
            Spacer(modifier = Modifier.size(35.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                              Icon(
                                  imageVector = Icons.Default.Email,
                                  contentDescription = "",
                                  tint = Color(0xC3B91C00)
                              )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                label = { Text(text = "Email", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(9.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "",
                        tint = Color(0xC3B91C00))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(30.dp))
            errorMessage?.let { message ->
                Text(
                    text = message,
                    color = Color.Red,
                    fontSize = 15.sp,
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            Button(onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    signInViewModel.signIn(
                        email = email,
                        password = password,
                        onSuccess = {
                            user?.let {
                                navToHome(it)
                            }
                        },
                        onError = { message ->
                            errorMessage = message
                        }

                    )
                } else {
                    errorMessage = "Vui lòng điền thông tin"
                }
                             },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 15.dp,
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = red
                ),
                border = BorderStroke(0.5.dp, Color.Red)
            ) {
                Text(text = "Sign In", fontWeight = FontWeight.Bold,fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.size(19.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Is it first for you? ", textAlign = TextAlign.Center)
                TextButton(onClick = {
                    navController.navigate(Screen.SignUpScreen.rout)
                },
                ) {
                    Text(text = "Sign Up now!",fontSize = 15.sp, fontWeight = FontWeight.Bold, color = red)
                }
            }
//            Spacer(modifier = Modifier.size(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
                Text(text = "OR Sign In with")
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            border = BorderStroke(0.5.dp, Color(0xFFD9D9D9)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gg),
                        contentDescription = "Logo GG",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            border = BorderStroke(0.5.dp, Color(0xFFD9D9D9)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.fb),
                        contentDescription = "Logo FB",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                }
            }

        }
}

