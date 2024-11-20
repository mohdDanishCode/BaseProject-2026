package com.omniful.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Unspecified
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.rememberAsyncImagePainter
import com.omniful.designsystem.theme.LocalOMFColors
import com.omniful.designsystem.theme.OmnifulTheme
import com.omniful.network.retrofit.RetrofitOmnifulNetwork
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    @Inject
    lateinit var  retrofitOmnifulNetwork : RetrofitOmnifulNetwork

    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->  }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofitOmnifulNetwork.initialize("https://api.restful-api.dev/")


//        CoroutineScope(Dispatchers.IO+coroutineExceptionHandler).launch {
//            val list = retrofitOmnifulNetwork.networkApi?.getProducts()
//            list?.forEach {
//                println(it)
//            }
//        }

        enableEdgeToEdge()
        setContent {
            OmnifulTheme  {
                val colors = LocalOMFColors.current


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.background)
                ) {
                    Greeting(
                        name = "Danish",
                        modifier = Modifier
                    )
                    val localOMFColors = LocalOMFColors.current

                    val imageLoader = rememberAsyncImagePainter(
                        model = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Rotating_earth_%28large%29.gif/200px-Rotating_earth_%28large%29.gif"
                    )

//                    Image(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        contentScale = ContentScale.Crop,
//                        painter = imageLoader,
//                        contentDescription = null
//                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

