package com.omniful.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.omniful.app.routes.AppNavigator
import com.omniful.app.routes.LocalAppNavigator
import com.omniful.app.routes.NavGraph
import com.omniful.designsystem.theme.OmnifulTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var appNavigator: AppNavigator


    @SuppressLint("UnsafeIntentLaunch")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")
        WindowCompat.setDecorFitsSystemWindows(window, true)


        setContent {

            OmnifulTheme  {

                val navController = rememberNavController()
                appNavigator = AppNavigator(navController,this)
                Scaffold(

                ) { padding ->
                    CompositionLocalProvider(LocalAppNavigator provides appNavigator) {
                        NavGraph(navController,padding)
                    }
                }


            }
        }
    }
}



