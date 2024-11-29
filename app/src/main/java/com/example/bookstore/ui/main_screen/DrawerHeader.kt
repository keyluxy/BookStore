package com.example.bookstore.ui.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookstore.R
import com.example.bookstore.ui.theme.Orange


@Composable
fun DrawerHeader(email: String) {
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(Orange),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(70.dp),
            painter = painterResource(
                id = R.drawable.book_logo),
                contentDescription = "")

        
        Spacer(modifier = Modifier.height(10.dp))
        
        Text(
            text = "Books Store",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            email,
            color = Color.Gray,
            fontSize = 16.sp,
        )
    }
}