package com.example.bookstore.ui.main_screen.button_screen

import com.example.bookstore.R

sealed class ButtonMenuItem(
    val route: String,
    val title: String,
    val iconId: Int
) {

    object Home : ButtonMenuItem(
        route = "",
        title = "Home",
        iconId = R.drawable.ic_home
    )

    object Favs : ButtonMenuItem(
        route = "",
        title = "Favorities",
        iconId = R.drawable.ic_favorities
    )

    object Settings : ButtonMenuItem(
        route = "",
        title = "Settings",
        iconId = R.drawable.ic_settings
    )
}