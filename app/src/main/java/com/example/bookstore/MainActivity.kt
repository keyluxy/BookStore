package com.example.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookstore.ui.add_book_screen.AddBookScreen
import com.example.bookstore.ui.add_book_screen.data.AddScreenObject
import com.example.bookstore.ui.login.LoginScreen
import com.example.bookstore.ui.login.data.LoginScreenObject
import com.example.bookstore.ui.login.data.MainScreenDataObject
import com.example.bookstore.ui.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LoginScreenObject
            ) {
                composable<LoginScreenObject> {
                    LoginScreen { navData ->
                        navController.navigate((navData))
                    }
                }

                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen(navData) {
                        navController.navigate(AddScreenObject)
                    }
                }

                composable<AddScreenObject> { navEntry ->
                    AddBookScreen {
                        navController.popBackStack()
                    }
                }

            }
        }

    }
}

