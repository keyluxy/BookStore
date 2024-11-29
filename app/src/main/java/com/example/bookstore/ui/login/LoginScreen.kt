package com.example.bookstore.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import com.example.bookstore.R
import com.example.bookstore.ui.login.data.MainScreenDataObject
import com.example.bookstore.ui.theme.BoxFilterColor
import com.google.firebase.Firebase
import com.google.firebase.annotations.concurrent.Background
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlin.math.sign


@Composable
fun LoginScreen(
    onNavigationToMainScreen: (MainScreenDataObject) -> Unit
) {

    val auth = remember { Firebase.auth }

    val errorState = remember { mutableStateOf("") }

    val emailState = remember { mutableStateOf("") }

    val passwordState = remember { mutableStateOf("") }

    Image(
        painter = painterResource(
            id = R.drawable.books_bg
        ),
        contentDescription = "BG Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor)
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 35.dp,
                end = 35.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.book_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(140.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Books Store",
            color = Color.White,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerTextField(
            text = emailState.value,
            label = "Email"
        ) {
            emailState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password"
        ) {
            passwordState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        LoginButton(text = "Sign In") {
            signIn(
                auth,
                emailState.value,
                passwordState.value,
                onSignInSuccess = { navData ->
                    onNavigationToMainScreen(navData)
                },
                onSignInFailure = { error ->
                    errorState.value = error

                }

            )
        }

        LoginButton(text = "Sign Up") {
            signUp(
                auth,
                emailState.value,
                passwordState.value,
                onSignUpSuccess = { navData ->
                    onNavigationToMainScreen(navData)
                },
                onSignUpFailure = { error ->
                    errorState.value = error

                }

            )
        }


    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: (MainScreenDataObject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {

    if (email.isBlank() || password.isBlank()) {
        onSignUpFailure("Email abd password cannot be empty")
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignUpSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign Up Error")
        }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (MainScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
) {

    if (email.isBlank() || password.isBlank()) {
        onSignInFailure("Email abd password cannot be empty")
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign In Error")
        }
}