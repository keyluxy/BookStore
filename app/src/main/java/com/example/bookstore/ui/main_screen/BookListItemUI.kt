package com.example.bookstore.ui.main_screen

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.bookstore.data.Book


@Composable
fun BookListItemUI(
    showEditButton: Boolean = false,
    book: Book,
    function: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        val base64Image = Base64.decode(book.imageUrl, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(
                base64Image,
                0, base64Image.size
            )

        AsyncImage(
            model = bitmap,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))
        
        Text(
            text = book.title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = book.description,
            color = Color.Gray,
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().width(1.dp),
                text = book.price,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                fontSize = 18.sp
            )
            
            if (showEditButton) IconButton(onClick = {

            }) {
                Icon(Icons.Default.Edit, contentDescription = "")
            }
        }


    }
}