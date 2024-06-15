package com.example.pizzeria.register

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Place
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.example.pizzeria.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
//    navToHome:(User) -> Unit,
    navToSignIn: () -> Unit,
    signUpViewModel: SignUpViewModel = viewModel()
){
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var address by remember { mutableStateOf("") }
        Box(modifier = Modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(id = R.drawable.bg_resgister),
                contentDescription = "",
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds)
        }
//        Spacer(modifier = Modifier.size(30.dp))
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                Text(text = "Welcome To ", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(text = " PIZZERIA!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = red,)
            }
            Spacer(modifier = Modifier.size(30.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name =it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "",
                        tint = Color(0xC3B91C00)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                label = { Text(text = "Username", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = "",
                        tint = Color(0xC3B91C00)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                label = { Text(text = "Email", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = phone,
                onValueChange = { phone =it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = "",
                        tint = Color(0xC3B91C00)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                label = { Text(text = "Phone number", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = address,
                onValueChange = { address=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Place,
                        contentDescription = "",
                        tint = Color(0xC3B91C00)
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                label = { Text(text = "Address", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password=it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "",
                        tint = Color(0xC3B91C00))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword =it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
//                    .focusRequester(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Lock,
                        contentDescription = "",
                        tint = Color(0xC3B91C00))
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Transparent,
                    focusedBorderColor = red,
                    unfocusedBorderColor = red
                ),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Confirm Password", color = Color(0xC3B91C00))},
            )
            Spacer(modifier = Modifier.size(20.dp))
            errorMessage?.let { message ->
                androidx.compose.material.Text(
                    text = message,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = {
                if (password == confirmPassword) {
                    signUpViewModel.signUp(
                        email = email,
                        password = password,
                        fullName = name,
                        phoneNumber = phone,
                        address = address,
                        onSuccess = {
                                    navToSignIn()
                        },
                        onError = { message ->
                            errorMessage = message
                        }
                    )
                } else {
                    errorMessage = "Mật khẩu không khớp."
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
                Text(text = "Sign Up", fontWeight = FontWeight.Bold,fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center,verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account?",textAlign = TextAlign.Center)

                TextButton(onClick = {
                    navToSignIn()
                },
                ) {
                    Text(text = "Sign In now!",fontSize = 16.sp, fontWeight = FontWeight.Bold, color = red)
                }
            }
//            Spacer(modifier = Modifier.size(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
                Text(text = "OR Sign Up with")
            }
            Spacer(modifier = Modifier.size(10.dp))
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

