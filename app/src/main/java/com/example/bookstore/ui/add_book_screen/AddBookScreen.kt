package com.example.bookstore.ui.add_book_screen

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.ui.login.LoginButton
import com.example.bookstore.ui.login.RoundedCornerTextField
import com.example.bookstore.ui.theme.BoxFilterColor
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage

@Composable
fun AddBookScreen(
    onSaved: () -> Unit = {}
) {

    var selectedCategory = "Fantazy"

    val cv = LocalContext.current.contentResolver

    val title = remember { mutableStateOf("") }

    val description = remember { mutableStateOf("") }

    val price = remember { mutableStateOf("") }

    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val firestore = remember {
        Firebase.firestore
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri.value = uri
        
    }

    Image(
        painter = rememberAsyncImagePainter(model = selectedImageUri.value),
        contentDescription = "BG Image",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f

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
            modifier = Modifier.size(90.dp)
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Add New Book",
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerDropDownMenu { selectesItem ->
            selectedCategory = selectesItem
        }

        Spacer(modifier = Modifier.height(15.dp))

        RoundedCornerTextField(
            text = title.value,
            label = "Title"
        ) {
            title.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))


        RoundedCornerTextField(
            maxLines = 5,
            singleLine = false,
            text = description.value,
            label = "Description"
        ) {
            description.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))

        RoundedCornerTextField(
            text = price.value,
            label = "Price"
        ) {
            price.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))



        LoginButton(text = "Select Image") {
            imageLauncher.launch("image/*")
        }

        LoginButton(text = "Save") {
            saveBookToFireStore(
                firestore,
                Book(
                    title = title.value,
                    description = description.value,
                    price = price.value,
                    category = selectedCategory,
                    imageUrl = imageToBase64(
                        selectedImageUri.value!!,
                        cv
                    )
                ),
                onSaved = {
                    onSaved()
                },
                onError = {
                    Log.e("MyLog", "Failed to save book")
                }


            )
        }


    }

}

@SuppressLint("Recycle")
private fun imageToBase64(
    uri: Uri,
    contentResolver: ContentResolver
): String {

    val inputStream = contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes()
    return bytes?.let {
        Base64.encodeToString(it, Base64.DEFAULT)
    } ?: ""

}

private fun saveBookToFireStore(
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
) {
    val db = firestore.collection("books")
    val key = db.document().id

    db.document(key)
        .set (
            book.copy(key = key)
        )
        .addOnSuccessListener {
            onSaved()
        }
        .addOnFailureListener {
            onError()
        }
}