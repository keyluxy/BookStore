package com.example.bookstore.ui.main_screen.button_screen

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource

@Composable
fun ButtonMenu() {

    val items = listOf(
        ButtonMenuItem.Home,
        ButtonMenuItem.Favs,
        ButtonMenuItem.Settings
    )

    val selectesItem = remember {
        mutableStateOf("Home")
    }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectesItem.value == item.title,
                onClick = {
                    selectesItem.value = item.title
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = "" )
                },

                label = {
                    Text(text = item.title)
                }
            )
        }


    }
}